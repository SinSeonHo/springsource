package com.example.relation.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.relation.entity.team.Team;
import com.example.relation.entity.team.TeamMember;
import com.example.relation.repository.team.TeamMemberRepository;
import com.example.relation.repository.team.TeamRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
public class TeamRepositoryTest {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Test
    public void insertTest() {
        // 팀(부모) 정보 삽입
        Team team = teamRepository.save(Team.builder().teamName("team2").build());

        // 회원(자식) 정보 삽입
        // teamMemberRepository.save(TeamMember.builder().userName("user1").team(team).build());

    }

    @Test
    public void insertTest2() {
        // 팀 정보 조회
        Team team = teamRepository.findById(1L).get();

        // 회원 정보 삽입
        teamMemberRepository.save(TeamMember.builder().userName("user2").team(team).build());

    }

    @Test
    public void readTest1() {
        // 팀 정보 조회
        Team team = teamRepository.findById(1L).get();

        // 회원 정보 조회
        TeamMember teamMember = teamMemberRepository.findById(1L).get();

        System.out.println(team);
        System.out.println(teamMember);
    }

    @Test
    public void readTest2() {

        // 회원 정보 조회
        TeamMember teamMember = teamMemberRepository.findById(1L).get();

        System.out.println(teamMember);
        // 객체그래프 탐색
        System.out.println(teamMember.getTeam());
    }

    @Test
    public void readTest3() {
        Team team = Team.builder().id(2L).build();
        List<TeamMember> list = teamMemberRepository.findByTeam(team);
        System.out.println(list); // [TeamMember(id=1, userName=user1), TeamMember(id=2, userName=user2)]
    }

    @Test
    public void findByMemberEqualTeamTest() {
        List<Object[]> result = teamMemberRepository.findByMemberEqualTeam(2L);

        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }

    }

    @Test
    public void updateTest() {
        // 1번 팀원의 팀 변경 : 2번 팀으로 변경

        // 회원 정보 조회
        TeamMember teamMember = teamMemberRepository.findById(1L).get();
        Team team = teamRepository.findById(2L).get();
        teamMember.setTeam(team);

        teamMemberRepository.save(teamMember);
    }

    @Test
    public void deleteTest() {
        // 1번 팀원의 팀 삭제

        // ORA-02292: 무결성 제약조건(C##JAVA.FK9UBP79EI4TV4CRD0R9N7U5I6E)이 위배되었습니다- 자식 레코드가
        // 발견되었습니다
        // teamRepository.deleteById(1L);

        // 해결법
        // 1. 삭제하려고하는 팀의 팀원들을 다른 팀으로 이동하거나 null 값으로 세팅
        // 2. 자식 삭제 후 부모 삭제

        // 1번 팀원의 팀을 2번 팀으로 변경
        TeamMember teammember = teamMemberRepository.findById(2L).get();
        Team team = teamRepository.findById(2L).get();
        teammember.setTeam(team);
        teamMemberRepository.save(teammember);

        // 팀 삭제
        teamRepository.deleteById(1L);

    }

    // -------------------------------
    // 양방향 연관관계 : @OneToMany
    // 단방향 2개를 염
    // -------------------------------

    // 팀 => 회원 접근
    // @Transactional
    @Test
    public void readBiTest1() {

        // 팀 찾기
        Team team = teamRepository.findById(2L).get();
        System.out.println(team);

        // 객체그래프 탐색
        team.getMembers().forEach(member -> System.out.println(member));
    }

    // -----------------------
    // 양방향
    // 영속성 전이 : Cascade
    // -----------------------
    @Test
    public void insertTest3() {
        // 팀 정보 조회
        Team team = Team.builder().teamName("team3").build();

        // 회원 정보 삽입
        TeamMember member = TeamMember.builder().userName("홍길동").team(team).build();
        team.getMembers().add(member);

        // teamMemberRepository.save(member);
        // 위 구문 없이 영속성 전이로 인해 부모에 저장하면 자식에도 자동으로 들어감
        teamRepository.save(team);
    }

    @Test
    public void deleteTest2() {
        // 부모 삭제 시 자식도 같이 삭제됨
        // deleteTest() 와 비교
        teamRepository.deleteById(3L);
    }

}
