package com.example.novels.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.novels.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

}
