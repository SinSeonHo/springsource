package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EntityListeners(value = AuditingEntityListener.class)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder

@Table(name = "studenttbl") // 쿼리실행 시 생성될 테이블 명 지정
@Entity // == db table과 연동된다라고 생각하면됨
public class Student {

    @Id // 무조건 있어야 함 pk주는용도
    @SequenceGenerator(name = "student_seq_gen", sequenceName = "student_seq", allocationSize = 1) // 시퀀스 지정
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq_gen")
    // @GeneratedValue // create sequence studenttbl_seq start with 1 increment by
    // 50
    private Long id; // id number(19,0) not null primary key (id)

    // @Column(name = "sname", length = 100, nullable = false, unique = true)
    // 컬럼명, 길이, not null, unique 등 지정
    // @Column(name = "sname", columnDefinition = "varchar2(20) not null unique")
    @Column(length = 20, nullable = false)
    private String name; // name varchar2(255 char)

    // @Column(columnDefinition = "number(8,0)")
    // private int grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Grade grade;

    @Column(columnDefinition = "varchar2(1) CONSTRAINT chk_gender CHECK (gender IN('M','F'))")
    private String gender;

    // LocalDateTime타입은 timestamp(6)로 생성됨
    @CreationTimestamp // insert 할 때
    private LocalDateTime cDateTime; // 실제 db상에서는 C_DATE_TIME로 생성됨

    @UpdateTimestamp // insert + 데이터 수정 할 때 마다
    private LocalDateTime uDateTime; // 실제 db상에서는 U_DATE_TIME로 생성됨

    @CreatedDate
    private LocalDateTime cDateTime2;
    @LastModifiedDate
    private LocalDateTime uDateTime2;

    // enum 정의
    // enum : 변경되지않는 상수들의 집합
    public enum Grade {
        FRESHMAN, SOPHOMORE, JUNIOR, SENIOR
    }

}
