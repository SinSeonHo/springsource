package com.example.novels.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchNovelRepository {

    // 하나조회
    Object[] getNovelById(Long id);

    // 페이지 나누기 + 조회 + 검색
    Page<Object[]> list(Long gid, String keyword, Pageable pageable);
}
