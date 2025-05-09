package com.example.security.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.security.entity.ClubMember;
import java.util.List;

public interface ClubMemberRepository extends JpaRepository<ClubMember, String> {
    // email 과 fromSocial 여부 확인

    @EntityGraph(attributePaths = { "roleSet" }, type = EntityGraphType.LOAD)
    ClubMember findByEmailAndFromSocial(String email, boolean fromSocial);
}
