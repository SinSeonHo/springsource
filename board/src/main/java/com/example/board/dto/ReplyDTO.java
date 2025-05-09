package com.example.board.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyDTO {

    private Long rno;

    private String text;

    private String replyer;

    // 게시글번호 (부모번호)
    private Long bno;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;
}