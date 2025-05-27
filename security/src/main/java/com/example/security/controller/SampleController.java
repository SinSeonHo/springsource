package com.example.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

    @PreAuthorize("permitAll()")
    @GetMapping("/guest")
    public void getGuest() {
        log.info("guest");
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'MEMBER')")
    @GetMapping("/member")
    public void getMember() {
        log.info("member");
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/admin")
    public void getAdmin() {
        log.info("admin");
    }

}
