package com.yzmglstm.entities;

import java.util.Date;
import java.util.List; // <-- YENİ EKLENDİ (İlişki için)

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType; // <-- YENİ EKLENDİ (İlişki için)
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType; // <-- YENİ EKLENDİ (İlişki için)
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany; // <-- YENİ EKLENDİ (İlişki için)
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name",nullable = false,length = 100)
    private String firstName;

    @Column(name="last_name",nullable = false,length=100)
    private String lastName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name="birth_date")
    private Date birthDate;

    @Column(name="mail",nullable = false)
    private String mail;

    @Column(name="phone",nullable = false,length = 11)
    private String phone;

    @Column(name="password",nullable = false)
    private String password;
    
    
    // --- YENİ EKLENEN "ONE-TO-MANY" İLİŞKİSİ ---
    // Bir Kullanıcının, birden çok Görevi (Tasks) olabilir.
    
    @OneToMany(
            mappedBy = "user", // Bu, Tasks.java sınıfındaki 'user' alanına bağlı olduğunu söyler.
            cascade = CascadeType.ALL, // Bir kullanıcı silinirse, tüm görevlerini de sil.
            fetch = FetchType.LAZY // Görevleri sadece ihtiyaç olduğunda yükle.
    )
    private List<Tasks> tasks; // Kullanıcının görevlerinin listesi
    
    // ------------------------------------------
}