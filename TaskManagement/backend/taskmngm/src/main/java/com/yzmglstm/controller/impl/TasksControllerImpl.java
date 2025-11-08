package com.yzmglstm.controller.impl;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.http.ResponseEntity; // <-- YENİ IMPORT
import org.springframework.web.bind.annotation.DeleteMapping; // <-- YENİ IMPORT
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; // <-- YENİ IMPORT
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping; // <-- YENİ IMPORT
import org.springframework.web.bind.annotation.RequestBody; 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yzmglstm.controller.ITasksController;
import com.yzmglstm.dto.DtoTask;
import com.yzmglstm.dto.DtoTaskIU;
import com.yzmglstm.services.ITasksServices;

import java.util.List;

@RestController
@RequestMapping("/api/tasks") 
public class TasksControllerImpl implements ITasksController {

    @Autowired 
    private ITasksServices tasksServices;

    // --- GÖREV EKLEME (POST) ---
    @Override
    @PostMapping 
    public DtoTask saveTasks(@RequestBody DtoTaskIU dtoTasks) { 
        return tasksServices.saveTask(dtoTasks);
    }
    
    // --- GÖREV LİSTELEME (GET) ---
    @Override
    @GetMapping
    public List<DtoTask> GetAllTasks() {
        return tasksServices.GetAllTasks();
    }
    
    // --- YENİ EKLENEN METOT: GÖREV DÜZENLEME (PUT /api/tasks/{id}) ---
    @Override
    @PutMapping("/{id}") // {id} adresin değişken bir parçası olduğunu söyler
    public DtoTask updateTask(@PathVariable Long id, @RequestBody DtoTaskIU dtoTasks) {
        // @PathVariable, adresteki {id}'yi alıp Long id değişkenine atar
        return tasksServices.updateTask(id, dtoTasks);
    }

    // --- YENİ EKLENEN METOT: GÖREV SİLME (DELETE /api/tasks/{id}) ---
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        tasksServices.deleteTask(id);
        // Silme işlemi başarılı olduğunda standart cevap HTTP 204 (No Content)
        return ResponseEntity.noContent().build();
    }
}