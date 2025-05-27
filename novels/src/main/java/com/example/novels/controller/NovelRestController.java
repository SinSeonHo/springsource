package com.example.novels.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.novels.dto.NovelDTO;
import com.example.novels.dto.PageRequestDTO;
import com.example.novels.dto.PageResultDTO;
import com.example.novels.service.NovelService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@Log4j2
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class NovelRestController {

    private final NovelService novelService;

    // 전체리스트
    @GetMapping("")
    public PageResultDTO<NovelDTO> getList(PageRequestDTO requestDTO) {
        log.info("전체 도서 정보 조회 {} ", requestDTO);
        PageResultDTO<NovelDTO> result = novelService.getList(requestDTO);
        return result;
    }

    // 하나조회
    @GetMapping("/{id}")
    public NovelDTO getRow(@PathVariable Long id) {
        log.info("도서 get {}", id);
        NovelDTO novelDTO = novelService.getRow(id);
        return novelDTO;
    }

    // 입력
    @PostMapping("/add")
    public Long postInsertNovel(@RequestBody NovelDTO novelDTO) {
        log.info("도서 post 추가 {}", novelDTO);
        return novelService.novelInsert(novelDTO);
    }

    // 수정
    @PutMapping("/{id}")
    public Long putNovel(@RequestBody NovelDTO novelDTO) {
        log.info("도서 put 수정 {}", novelDTO);
        return novelService.avaUpdate(novelDTO);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public Long putMethodName(@PathVariable Long id) {
        log.info("도서 delete 삭제 {}", id);
        novelService.novelRemove(id);
        return id;
    }

}
