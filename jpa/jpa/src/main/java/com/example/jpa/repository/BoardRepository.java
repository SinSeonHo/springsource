package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.example.jpa.entity.Board;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer>, QuerydslPredicateExecutor<Board> {

    // // WHERE b.WRITER = '홍길동4'
    // List<Board> findByWriter(String writer);

    // // title 이 title1번인 게시물 조회
    // // WHERE b.TITLE = 'title1';
    // List<Board> findByTitle(String title);

    // // WHERE b.TITLE LIKE 'title%';
    // List<Board> findByWriterStartingWith(String writer);

    // // WHERE b.TITLE LIKE '%title';
    // List<Board> findByWriterEndingWith(String writer);

    // // WHERE b.TITLE LIKE '%title%';
    // List<Board> findByWriterContaining(String writer);

    // // WHERE b.WRITER like '%홍길동%' or b.content like '%content%'
    // List<Board> findByWriterContainingOrContentContaining(String writer, String
    // content);

    // // WHERE b.WRITER like '%홍길동%' and b.content like '%content%'
    // List<Board> findByWriterContainingAndContentContaining(String writer, String
    // content);

    // // bno > 5 인 게시물 조회
    // List<Board> findByBnoGreaterThan(Long bno);

    // // bno > 0 order by bno desc
    // List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno);

    // // bno >= 5 and bno <= 10
    // // where bno between 5 and 10
    // List<Board> findByBnoBetween(Long startbno, Long endbno);

    // ---------------------------------------------------------------
    // @Query 이용
    // ---------------------------------------------------------------
    // select 별칭 from 엔티티명 별칭 where 별칭.칼럼
    // @Query("select b from Board b where b.writer = ?1")
    @Query("select b from Board b where b.writer = :writer")
    List<Board> findByWriter(@Param("writer") String writer);

    @Query("select b from Board b where b.writer LIKE ?1%")
    List<Board> findByWriterStartingWith(String writer);

    @Query("select b from Board b where b.writer LIKE %?1%")
    List<Board> findByWriterContaining(String writer);

    // @Query("select b from Board b where b.bno > ?1")

    @Query("select b.title, b.writer from Board b where b.title LIKE %?1%")
    List<Object[]> findByTitle2(String title); // 2개부터는 오브젝트 배열이 됨

    // 실제 sql 구문 형식 사용방법
    // @Query(value = "select * from Board b where b.bno > ?1", nativeQuery = true)
    @NativeQuery("select * from Board b where b.bno > ?1")
    List<Board> findByBnoGreaterThan(Long bno);
}
