package com.yzmglstm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate; // YENİ
import java.time.LocalTime; // YENİ

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoTask {
    
    private Long id;
    private String title;
    private String description;
    private String category;
    private String status;
    private LocalDate dueDate; // Date yerine LocalDate
    private LocalTime dueTime; // Date yerine LocalTime
}