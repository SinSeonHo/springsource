package com.example.novels.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.novels.dto.NovelDTO;
import com.example.novels.dto.PageRequestDTO;
import com.example.novels.dto.PageResultDTO;
import com.example.novels.entity.Genre;
import com.example.novels.entity.Novel;
import com.example.novels.repository.GradeRepository;
import com.example.novels.repository.NovelRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class NovelService {

    private final NovelRepository novelRepository;
    private final GradeRepository gradeRepository;

    public Long avaUpdate(NovelDTO novelDTO) {

        // available 변경
        Novel novel = novelRepository.findById(novelDTO.getId()).get();
        novel.changeAvailable(novelDTO.isAvailable());
        return novelRepository.save(novel).getId();

    }

    public Long pubUpdate(NovelDTO novelDTO) {

        // publishedDate 변경
        Novel novel = novelRepository.findById(novelDTO.getId()).get();
        novel.changePublishedDate(novelDTO.getPublishedDate());
        return novelRepository.save(novel).getId();

    }

    @Transactional
    public void novelRemove(Long id) {

        // 자식에 해당하는 grade 삭제가 먼저 이루어져야 함
        gradeRepository.deleteByNovel(Novel.builder().id(id).build());

        // novel 삭제
        novelRepository.deleteById(id);

    }

    public Long novelInsert(NovelDTO novelDTO) {

        // novel 추가

        Novel novel = Novel.builder()
                .title(novelDTO.getTitle())
                .author(novelDTO.getAuthor())
                .publishedDate(novelDTO.getPublishedDate())
                .available(novelDTO.isAvailable())
                .genre(Genre.builder().id(novelDTO.getGid()).build())
                .build();
        return novelRepository.save(novel).getId();
    }

    public PageResultDTO<NovelDTO> getList(PageRequestDTO pageRequestDTO) {

        // 최신리스트 10개 불러오기
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize(),
                Sort.by("id").descending());
        Page<Object[]> result = novelRepository.list(pageRequestDTO.getGenre(), pageRequestDTO.getKeyword(), pageable);

        // entity -> dto
        List<NovelDTO> dtoList = result.get().map(arr -> {
            Novel novel = (Novel) arr[0];
            Genre genre = (Genre) arr[1];
            Double rating = (Double) arr[2];

            NovelDTO novelDTO = NovelDTO.builder()
                    .id(novel.getId())
                    .title(novel.getTitle())
                    .author(novel.getAuthor())
                    .publishedDate(novel.getPublishedDate())
                    .available(novel.isAvailable())
                    .gid(genre.getId())
                    .genreName(genre.getName())
                    .rating(rating != null ? rating.intValue() : 0)
                    .build();
            return novelDTO;
        }).collect(Collectors.toList());

        long totalCount = result.getTotalElements();
        return PageResultDTO.<NovelDTO>withAll().dtoList(dtoList).totalCount(totalCount).pageRequestDTO(pageRequestDTO)
                .build();
    }

    public NovelDTO getRow(Long id) {

        // 1개 불러오기
        Object[] result = novelRepository.getNovelById(id);

        Novel novel = (Novel) result[0];
        Genre genre = (Genre) result[1];
        Double avgGrade = (Double) result[2];
        NovelDTO dto = entityToDto(novel, genre, avgGrade);
        return dto;

    }

    private NovelDTO entityToDto(Novel novel, Genre genre, Double rating) {
        NovelDTO novelDTO = NovelDTO.builder()
                .id(novel.getId())
                .title(novel.getTitle())
                .author(novel.getAuthor())
                .publishedDate(novel.getPublishedDate())
                .available(novel.isAvailable())
                .gid(genre.getId())
                .genreName(genre.getName())
                .rating(rating != null ? rating.intValue() : 0)
                .build();
        return novelDTO;
    }
}
