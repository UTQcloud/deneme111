package com.yzmglstm.controller;

import com.yzmglstm.dto.DtoTask;
import com.yzmglstm.dto.DtoTaskIU;
import java.util.List;
import org.springframework.http.ResponseEntity; // <-- YENİ IMPORT

public interface ITasksController {

    DtoTask saveTasks(DtoTaskIU dtoTasks);
    
    List<DtoTask> GetAllTasks();
    
    // --- YENİ EKLENEN METOT İMZALARI ---
    
    DtoTask updateTask(Long id, DtoTaskIU dtoTasks);
    
    ResponseEntity<Void> deleteTask(Long id);

}