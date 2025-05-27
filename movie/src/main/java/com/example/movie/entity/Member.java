package com.example.movie.entity;

import java.util.HashSet;
import java.util.Set;

import groovyjarjarantlr4.v4.parse.BlockSetTransformer.setAlt_return;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "m_member")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Entity

public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mid;

    @Column(unique = true)
    private String email;
    private String password;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    public void changeNickname(String nickname) {
        this.nickname = nickname;
    }

    public void changePassword(String password) {
        this.password = password;
    }
    // @ElementCollection(fetch = FetchType.LAZY) // 1:N 관계로 테이블 생성해줌
    // @Builder.Default
    // private Set<MemberRole> roleSet = new HashSet<>();

    // public void addMemberRole(MemberRole memberRole) {
    // roleSet.add(memberRole);
    // }

}
