package com.example.relation.repository.team;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.relation.entity.team.Team;
import com.example.relation.entity.team.TeamMember;
import java.util.List;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    // team을 기준으로 member를 찾기 findBy~ 이용
    List<TeamMember> findByTeam(Team team);

    // team을 기준으로 member를 찾기 => member, team정보 조회
    @Query("select m, t from TeamMember m join m.team t where t.id = :id")
    List<Object[]> findByMemberEqualTeam(Long id);

    // SELECT * FROM TEAM_MEMBER tm
    // JOIN TEAM t ON tm.TEAM_ID = t.TEAM_ID
    // WHERE tm.TEAM_ID = 2;
}
