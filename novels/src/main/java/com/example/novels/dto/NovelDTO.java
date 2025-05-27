package com.example.novels.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString

public class NovelDTO {

    private Long id;
    private String title;
    private String author;
    private LocalDate publishedDate;
    private boolean available;

    // 장르id
    private Long gid;

    // 장르명
    private String genreName;

    // 평점
    private int rating;
}
