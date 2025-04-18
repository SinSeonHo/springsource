package com.example.jpa.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Student;
import com.example.jpa.entity.Student.Grade;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest // test 용 클래스임을 알려줌
public class StudentRepositoryTest {

    @Autowired // = new StudentRepository()
    private StudentRepository studentRepository;

    // CRUD test
    // Repository, Entity 확인
    // C(insert) : save(Entity) 메소드사용
    // R
    // U
    // D(update) : save(Entity) 메소드사용
    // C, D 둘다 동일한 save() 호출하기에 구별하려면
    // 원본과 변경된 부분이 있다면 update로 실행

    @Test // test 용 메소드임을 알려줌 (테스트 메소드의 return타입은 void여야함)
    public void inserTest() {
        // Entity 생성

        LongStream.range(1, 11).forEach(i -> {
            Student student = Student.builder()
                    .name("홍길동" + i)
                    .grade(Grade.JUNIOR)
                    .gender("M")
                    .build();
            // insert 기능
            studentRepository.save(student);
        });

    }

    @Test
    public void updateTest() {

        // findById(1L) : select * from 테이블명 where id=1;

        Student student = studentRepository.findById(1L).get();

        // set을 무조건 전체 칼럼을 하기로 되어있음

        // update studenttbl
        // set
        // c_date_time=?,
        // c_date_time2=?,
        // gender=?,
        // grade=?,
        // name=?,
        // u_date_time=?,
        // u_date_time2=?
        // where
        // id=?
        student.setGrade(Grade.SENIOR);
        // update 기능
        studentRepository.save(student);
    }

    @Test
    public void selectOneTest() {

        // Optional<Student> student = studentRepository.findById(1L);

        // if (student.isPresent()) {
        // System.out.println(student.get());
        // }

        Student student = studentRepository.findById(3L).orElseThrow(EntityNotFoundException::new);
        System.out.println(student);
    }

    @Test
    public void selectTest() {
        // List<Student> list = studentRepository.findAll();

        // for (Student student : list) {
        // System.out.println(student);
        // }
        studentRepository.findAll().forEach(student -> System.out.println(student));
    }

    @Test
    public void deleteTest() {

        // Student student = studentRepository.findById(11L).get();
        // studentRepository.delete(student);

        studentRepository.deleteById(10L);
    }
}
