package com.yzmglstm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate; 
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoTaskIU {
    
    
    private String title;
    private String description;
    private String category;
    private String status;
    private LocalDate dueDate; 
    private LocalTime dueTime;
}