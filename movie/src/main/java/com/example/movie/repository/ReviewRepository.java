package com.example.movie.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;

import com.example.movie.entity.Member;
import com.example.movie.entity.Movie;
import com.example.movie.entity.Review;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Modifying // delete, update 시 반드시 필요함
    @Query("DELETE FROM Review r WHERE r.movie = :movie")
    void deleteByMovie(Movie movie);

    // 회원(멤버)탈퇴시 해당 멤버가 작성한 모든 리뷰 제거
    @Modifying // delete, update 시 반드시 필요함
    @Query("DELETE FROM Review r WHERE r.member = :member")
    void deleteByMember(Member member);

    // movie 아이디를 이용해 리뷰 가져오기
    @EntityGraph(attributePaths = "member", type = EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);

}
