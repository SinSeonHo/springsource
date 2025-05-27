package com.example.movie.task;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.movie.dto.MovieImageDTO;
import com.example.movie.entity.MovieImage;
import com.example.movie.repository.MovieImageRepository;

import lombok.extern.log4j.Log4j2;

// cron : 유닉스 계열 운영체제에서 사용하는 작업 스케쥴러

@Log4j2
@Component
public class FileCheckTask {

    @Autowired
    private MovieImageRepository movieImageRepository;

    @Value("${com.example.movie.upload.path}")
    private String uploadPath;

    // 전일날짜 폴더의 리스트 추출
    private String getFolderYesterday() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String str = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return str.replace("-", File.separator);
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void checkFile() {
        log.info("file Check Task...");

        // 데이터베이스에서 어제날짜 파일 목록 추출
        List<MovieImage> oldImages = movieImageRepository.getOldImages();
        // entity -> dto
        List<MovieImageDTO> movieImageDTOs = oldImages.stream().map(movieImage -> {
            return MovieImageDTO.builder()
                    .inum(movieImage.getInum())
                    .uuid(movieImage.getUuid())
                    .imgName(movieImage.getImgName())
                    .path(movieImage.getPath())
                    .build();
        }).collect(Collectors.toList());

        // java.nio.Path
        // 폴더에 들어있는 파일명과 일치하도록 db 내용 변경
        List<Path> fileListPaths = movieImageDTOs.stream()
                .map(dto -> Paths.get(uploadPath, dto.getImageURL(), dto.getUuid() + "_" + dto.getImgName()))
                .collect(Collectors.toList());

        movieImageDTOs.stream()
                .map(dto -> Paths.get(uploadPath, dto.getImageURL(), "s_" + dto.getUuid() + "_" + dto.getImgName()))
                .forEach(p -> fileListPaths.add(p));

        // 어제 날짜 폴더에 접근
        File targetDir = Paths.get(uploadPath, getFolderYesterday()).toFile();
        // 폴더에 가지고있는 파일명과 db에서 가지고 온 파일명을 비교 후 일치하지 않는 파일만 담기
        File[] removFiles = targetDir.listFiles(f -> fileListPaths.contains(f.toPath()) == false);

        // 파일 삭제
        if (removFiles != null) {
            for (File file : removFiles) {
                log.warn(file.getAbsolutePath());
                file.delete();
            }
        }
    }
}
