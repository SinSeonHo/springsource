package com.example.relation.entity.team;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 회원은 단 하나의 팀에 소속된다.
@ToString(exclude = "team")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class TeamMember {
    // id, name(회원명)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String userName;

    // JoinColumn -> 외래키 필드명 설정 해당 어노테이션을 미사용 시 table명_pk명 으로 설정됨
    @JoinColumn(name = "team_id")
    @ManyToOne // ManyToOne은 left join을 걸어서 가져옴
    private Team team;
}
