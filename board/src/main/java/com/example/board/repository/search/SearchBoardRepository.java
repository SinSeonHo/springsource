package com.example.board.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchBoardRepository {
    Page<Object[]> list(String type, String keyword, Pageable pageable);

    Object[] getBoardByBno(Long bno);

}
