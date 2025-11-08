package com.yzmglstm.services.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // <-- YENİ IMPORT

import com.yzmglstm.dto.DtoTask;
import com.yzmglstm.dto.DtoTaskIU;
import com.yzmglstm.entities.Tasks;
import com.yzmglstm.entities.Users;
import com.yzmglstm.repository.TasksRepository;
import com.yzmglstm.repository.UsersRepository;
import com.yzmglstm.services.ITasksServices;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional // Veritabanı işlemleri için bu notasyonu eklemek iyi bir pratiktir
public class TasksServicesImpl implements ITasksServices {

    private final TasksRepository tasksRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public TasksServicesImpl(TasksRepository tasksRepository, UsersRepository usersRepository) {
        this.tasksRepository = tasksRepository;
        this.usersRepository = usersRepository;
    }

    // --- YARDIMCI METOT (Giriş Yapan Kullanıcıyı Bul) ---
    private Users getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        return usersRepository.findByMail(userEmail)
                .orElseThrow(() -> new RuntimeException("Giriş yapmış kullanıcı veritabanında bulunamadı: " + userEmail));
    }
    
    // --- YARDIMCI METOT (Güvenlik Kontrolü) ---
    // Bu metot, bir görevin giriş yapan kullanıcıya ait olup olmadığını kontrol eder
    private Tasks checkTaskOwnerAndGet(Long taskId) {
        Users loggedInUser = getLoggedInUser();
        
        // Görevi ID ile bul
        Tasks task = tasksRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Görev bulunamadı: " + taskId));

        // GÖREVLİ GÜVENLİK KONTROLÜ
        // Görevin 'user_id'si ile giriş yapan kullanıcının 'id'si eşleşiyor mu?
        if (!task.getUser().getId().equals(loggedInUser.getId())) {
            // Eşleşmiyorsa, bu görev başkasına aittir. Hata fırlat.
            throw new RuntimeException("Yetkisiz erişim: Bu görevi düzenleme/silme izniniz yok.");
        }
        
        // Görev bu kullanıcıya aittir, güvenle geri döndür
        return task;
    }


    // --- GÖREV EKLEME (POST) ---
    @Override
    public DtoTask saveTask(DtoTaskIU dtoTasks) {
        Users loggedInUser = getLoggedInUser();
        Tasks tasks = new Tasks();
        BeanUtils.copyProperties(dtoTasks, tasks);
        tasks.setUser(loggedInUser); // Görevi kullanıcıya ata
        Tasks saveTasks = tasksRepository.save(tasks);
        DtoTask response = new DtoTask();
        BeanUtils.copyProperties(saveTasks, response);
        return response;
    }

    // --- GÖREV LİSTELEME (GET) ---
    @Override
    public List<DtoTask> GetAllTasks() {
        Users loggedInUser = getLoggedInUser();
        List<Tasks> userTasks = tasksRepository.findByUserId(loggedInUser.getId());
        List<DtoTask> dtoList = new ArrayList<>();
        for (Tasks task : userTasks) {
            DtoTask dto = new DtoTask();
            BeanUtils.copyProperties(task, dto);
            dtoList.add(dto);
        }
        return dtoList;
    }

    // --- YENİ EKLENEN METOT: GÖREV DÜZENLEME (PUT /api/tasks/:id) ---
    @Override
    public DtoTask updateTask(Long taskId, DtoTaskIU dtoTasks) {
        
        // 1. Güvenlik Kontrolü: Bu görev bu kullanıcıya mı ait?
        Tasks existingTask = checkTaskOwnerAndGet(taskId);
        
        // 2. Güncelleme: DTO'dan gelen yeni verileri mevcut göreve kopyala
        // (id ve user alanı hariç)
        BeanUtils.copyProperties(dtoTasks, existingTask, "id", "user");
        
        // 3. Kaydet: Değişiklikleri kaydet
        Tasks updatedTask = tasksRepository.save(existingTask);
        
        // 4. Cevapla
        DtoTask response = new DtoTask();
        BeanUtils.copyProperties(updatedTask, response);
        return response;
    }

    // --- YENİ EKLENEN METOT: GÖREV SİLME (DELETE /api/tasks/:id) ---
    @Override
    public void deleteTask(Long taskId) {
        
        // 1. Güvenlik Kontrolü: Bu görev bu kullanıcıya mı ait?
        Tasks taskToDelete = checkTaskOwnerAndGet(taskId);
        
        // 2. Sil: Görevi sil
        tasksRepository.delete(taskToDelete);
        
        // (Bu metot bir şey döndürmez, HTTP 204 No Content beklenir)
    }
}