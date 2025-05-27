package com.example.novels.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.novels.entity.Grade;
import com.example.novels.entity.Novel;

import jakarta.transaction.Transactional;

public interface GradeRepository extends JpaRepository<Grade, Long> {

    // Novel 이용하여 grade 제거하기

    @Modifying
    @Query("DELETE FROM Grade g WHERE g.novel = :novel")
    void deleteByNovel(Novel novel);

}
