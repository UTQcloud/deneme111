package com.yzmglstm.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnore; // <-- YENİ EKLENDİ (İlişki için)

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType; // <-- YENİ EKLENDİ (İlişki için)
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn; // <-- YENİ EKLENDİ (İlişki için)
import jakarta.persistence.ManyToOne; // <-- YENİ EKLENDİ (İlişki için)
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
// Veritabanında 'tasks' şemasını oluşturduğun için 'schema = "tasks"' ekliyoruz.
@Table(name="tasks", schema = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title",nullable = false,length = 100)
    private String title;

    @Column(name="description",nullable = false,length = 3000)
    private String description;

    @Column(name="category",nullable = false,length = 100)
    private String category;

    @Column(name="status",nullable = false)
    private String status;

    @Column(name="dueDate")
    private LocalDate dueDate; 

    @Column(name="dueTime")
    private LocalTime dueTime;

    // --- YENİ EKLENEN "MANY-TO-ONE" İLİŞKİSİ ---
    // Birçok Görev (Task), bir Kullanıcıya (User) aittir.
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // 'tasks' tablosuna 'user_id' adında bir kolon ekler.
    @JsonIgnore // API cevabında sonsuz döngü olmasın diye bu alanı gizler.
    private Users user;

    // ------------------------------------------
}