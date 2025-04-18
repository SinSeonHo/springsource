package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jpa.entity.Student;

// JpaRepository 상속 후 <Entity명, @Id타입>
public interface StudentRepository extends JpaRepository<Student, Long> {

}
