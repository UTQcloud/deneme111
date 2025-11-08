package com.yzmglstm.services;

import com.yzmglstm.dto.DtoTask;
import com.yzmglstm.dto.DtoTaskIU;
import java.util.List;

public interface ITasksServices {

    DtoTask saveTask(DtoTaskIU dtoTasks);
    
    List<DtoTask> GetAllTasks();
    
    // --- YENİ EKLENEN METOT İMZALARI ---
    
    DtoTask updateTask(Long taskId, DtoTaskIU dtoTasks);
    
    void deleteTask(Long taskId);

}