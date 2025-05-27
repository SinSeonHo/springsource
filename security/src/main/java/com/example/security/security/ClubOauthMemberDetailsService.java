package com.example.security.security;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.couchbase.ClusterEnvironmentBuilderCustomizer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.security.entity.ClubMember;
import com.example.security.entity.ClubMemberRole;
import com.example.security.repository.ClubMemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class ClubOauthMemberDetailsService extends DefaultOAuth2UserService {

    private final ClubMemberRepository clubMemberRepository;
    private final PasswordEncoder passwordEncoder;

    // ClubMemberDetailsService(ClubMemberRepository clubMemberRepository) {
    // this.clubMemberRepository = clubMemberRepository;
    // }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("username test {}", userRequest);

        // 소셜 계정이 넘겨주는 정보 추출
        String clientName = userRequest.getClientRegistration().getClientName();
        System.out.println("==============================");
        log.info("clientName {}", clientName);
        log.info(userRequest.getAdditionalParameters());
        log.info(userRequest);
        log.info("===============OAuth2User가 가지고 있는 값");

        OAuth2User oAuth2User = super.loadUser(userRequest);
        oAuth2User.getAttributes().forEach((k, v) -> {
            log.info("{} : {}", k, v);
        });

        String email = null;
        if (clientName.equals("Google")) {
            email = oAuth2User.getAttribute("email");
        }

        ClubMember member = saveSocialMember(email);

        ClubAuthMemberDTO clubAuthMemberDTO = new ClubAuthMemberDTO(member.getEmail(),
                member.getPassword(),
                member.isFromSocial(),
                member.getRoleSet().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                        .collect(Collectors.toList()),
                oAuth2User.getAttributes());
        clubAuthMemberDTO.setName(member.getName());

        return clubAuthMemberDTO;
    }

    private ClubMember saveSocialMember(String email) {
        // 이메일로 임시비밀번호 암호화 후 저장

        // 해당 이메일로 가입된 정보 있는지 확인
        ClubMember clubMember = clubMemberRepository.findByEmailAndFromSocial(email, true);

        if (clubMember == null) {
            ClubMember saveMember = ClubMember.builder()
                    .email(email)
                    .name(email)
                    .password(passwordEncoder.encode("1111"))
                    .fromSocial(true)
                    .build();

            saveMember.addMemberRole(ClubMemberRole.USER);
            clubMemberRepository.save(saveMember);
            return saveMember;
        }
        return clubMember;
    }

}
