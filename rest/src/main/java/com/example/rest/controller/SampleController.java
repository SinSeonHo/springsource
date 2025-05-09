package com.example.rest.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.rest.dto.SampleDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.LongStream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class SampleController {

    @GetMapping("/")
    public String getTest1() {
        return "Hello";
    }

    // 자바 객체는 브라우저에서 해석 불가
    // Jackson 라이브러리가 중간에서 작업 중

    @GetMapping("/sample")
    public SampleDTO getSample() {
        SampleDTO dto = new SampleDTO();
        dto.setName("hong");
        dto.setMno(1L);
        dto.setAge(25);

        return dto;
    }

    @GetMapping("/sample2")
    public List<SampleDTO> getSample2() {

        List<SampleDTO> list = new ArrayList<>();

        LongStream.rangeClosed(1, 10).forEach(i -> {
            SampleDTO dto = new SampleDTO();
            dto.setName("hong");
            dto.setMno(1L);
            dto.setAge(25);

            list.add(dto);
        });
        return list;

    }

    // 응답코드 + 데이터
    @GetMapping("/sample3")
    public ResponseEntity<SampleDTO> check(double weight) {
        SampleDTO dto = new SampleDTO();
        dto.setName("hong");
        dto.setMno(1L);
        dto.setAge(25);

        if (weight < 200) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<SampleDTO>(dto, HttpStatus.OK);
    }

    // @PathVariable -> ? 뒤가아닌 주소경로상에있는 값을 받아서 사용
    // http://localhost:8080/sample4/shirt/1234?weight=123
    @GetMapping("/sample4/{cat}/{pid}")
    public ResponseEntity<String[]> check2(@PathVariable String cat, @PathVariable String pid, Long weight) {
        String arr[] = {
                "category : " + cat, "productId : " + pid
        };

        return new ResponseEntity<>(arr, HttpStatus.OK);
    }

}
