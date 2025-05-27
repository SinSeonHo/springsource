package com.example.novels.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.novels.entity.Novel;

public interface NovelRepository extends JpaRepository<Novel, Long>, SearchNovelRepository {

}
