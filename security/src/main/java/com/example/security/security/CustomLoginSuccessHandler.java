package com.example.security.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        // 로그인 성공 시 어디로 이동할 것인가??
        // 기본은 로그인 작업 이전의 페이지로 이동함

        ClubAuthMemberDTO clubAuthMemberDTO = (ClubAuthMemberDTO) authentication.getPrincipal();
        log.info("CustomLoginSuccessHandler {}", clubAuthMemberDTO);

        // ROLE 에 따라 가는 경로를 다르게 한다면
        // ROLE_USER, ROLE_MANAGER, ROLE_ADMIN

        // ROLE 확인
        List<String> roleNames = new ArrayList<>();
        clubAuthMemberDTO.getAuthorities().forEach(auth -> {
            roleNames.add(auth.getAuthority());
        });

        log.info("roleNames {}", roleNames);

        if (roleNames.contains("ROLE_ADMIN")) {
            response.sendRedirect("/sample/admin");
            return;
        }

        if (roleNames.contains("ROLE_USER") || roleNames.contains("ROLE_MANAGER")) {
            response.sendRedirect("/sample/member"); // == controller "redirect:/sample/member"
            return;
        }

        response.sendRedirect("/");
    }

}
