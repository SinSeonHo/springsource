package com.example.todo.controller;

import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@Log4j2

public class HomeController {

    @GetMapping("/")
    public String getHome() {
        log.info("home 페이지 요청");
        return "home";
    }

}
