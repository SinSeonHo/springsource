package com.example.elastic1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.elastic1.dto.UserDocumentDTO;
import com.example.elastic1.service.UserService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserController {

    // http://localhost:8080/users + GET : 전체조회 => http://localhost:9200/
    // http://localhost:8080/users/id + GET : id에 해당하는 유저 조회
    // http://localhost:8080/users/id + PUT : id에 해당하는 유저 수정
    // http://localhost:8080/users/id + DELETE : id에 해당하는 유저 삭제

    private final UserService userservice;

    @GetMapping("")
    public List<UserDocumentDTO> getList() {
        return userservice.list();
    }

    @GetMapping("{id}")
    public UserDocumentDTO getRow(@PathVariable String id) {
        return userservice.getUser(id);
    }

    @PutMapping("{id}")
    public UserDocumentDTO putRow(@RequestBody UserDocumentDTO userDocumentDTO, @PathVariable String id) {
        userDocumentDTO.setId(id);
        // userservice.updateUser(userDocumentDTO);
        return userservice.getUser(id);
    }

    @DeleteMapping("{id}")
    public String deleteRow(@PathVariable String id) {
        userservice.removeUser(id);
        return id;
    }

}
