package com.example.jpa.repository;

import static org.mockito.ArgumentMatchers.booleanThat;

import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Board;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    // test 메소드 작성
    @Test
    public void insertTest() {
        LongStream.rangeClosed(1, 10).forEach(i -> {
            Board board = Board.builder()
                    .writer("홍길동" + i)
                    .title("title" + i)
                    .content("content" + i)
                    .build();

            // insert 기능
            boardRepository.save(board);
        });
    }

    @Test
    public void updateTest() {
        // Memo memo = Memo.builder().mno(1L).memoText("memoText update").build();
        Board board = boardRepository.findById(10).get();
        board.setContent("content update");
        boardRepository.save(board);
    }

    @Test
    public void readTest() {
        Board board = boardRepository.findById(5).get();
        System.out.println(board);
    }

    @Test
    public void listTest() {

        // studentRepository.findAll().forEach(student -> System.out.println(student));
        boardRepository.findAll().forEach(board -> System.out.println(board));
    }

    @Test
    public void deleteTest() {
        boardRepository.deleteById(7);
    }
}
