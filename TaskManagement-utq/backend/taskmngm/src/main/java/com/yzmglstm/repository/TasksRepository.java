package com.yzmglstm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.yzmglstm.entities.Tasks;
import java.util.List; // <-- BU SATIR YENİ EKLENDİ

@Repository
public interface TasksRepository extends JpaRepository<Tasks, Long> {

    // --- YENİ EKLENEN SORGU METODU ---
    // Spring Data JPA'nın "sihirli" metodudur.
    // Adından yola çıkarak "Tasks entity'si içindeki 'user' alanının 'id'sine göre"
    // (SELECT * FROM tasks.tasks WHERE user_id = ?) sorgusunu OTOMATİK oluşturur.
    
    List<Tasks> findByUserId(Long userId);
    
    // -----------------------------------
}