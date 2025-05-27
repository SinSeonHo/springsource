package com.example.movie.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.movie.dto.ReviewDTO;
import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;
import com.example.movie.repository.MemberRepository;
import com.example.movie.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Long insertReview(ReviewDTO reviewDTO) {

        Review review = dtoToEntity(reviewDTO);

        return reviewRepository.save(review).getRno();
    }

    public ReviewDTO getReview(Long rno) {
        Review review = reviewRepository.findById(rno).get();
        return entityToDto(review);
    }

    public ReviewDTO updateReview(ReviewDTO reviewDTO) {
        // dto => entity
        Review review = reviewRepository.findById(reviewDTO.getRno()).orElseThrow();

        // 수정
        review.changeGrade(reviewDTO.getGrade());
        review.changeText(reviewDTO.getText());
        review = reviewRepository.save(review);
        return entityToDto(review);
    }

    public void removeReview(Long rno) {
        reviewRepository.deleteById(rno);
    }

    public List<ReviewDTO> getReviews(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();
        List<Review> result = reviewRepository.findByMovie(movie);

        List<ReviewDTO> list = result.stream().map(review -> entityToDto(review)).collect(Collectors.toList());

        return list;
    }

    private Review dtoToEntity(ReviewDTO reviewDTO) {
        // ReviewDTO -> Review

        Review review = Review.builder()
                .rno(reviewDTO.getRno())
                .grade(reviewDTO.getGrade())
                .text(reviewDTO.getText())
                .member(Member.builder().mid(reviewDTO.getMid()).build())
                .movie(Movie.builder().mno(reviewDTO.getMno()).build())
                .build();
        return review;
    }

    private ReviewDTO entityToDto(Review review) {
        // Review -> ReviewDTO
        ReviewDTO reviewDTO = ReviewDTO.builder()

                // 리뷰 정보
                .rno(review.getRno())
                .grade(review.getGrade())
                .text(review.getText())
                .createdDate(review.getCreatedDate())
                .updatedDate(review.getUpdatedDate())

                // 멤버 정보
                .mid(review.getMember().getMid())
                .email(review.getMember().getEmail())
                .nickname(review.getMember().getNickname())
                .build();

        return reviewDTO;
    }
}
