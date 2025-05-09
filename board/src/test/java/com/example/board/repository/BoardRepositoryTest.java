package com.example.board.repository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.dto.PageRequestDTO;
import com.example.board.entity.Board;
import com.example.board.entity.Member;
import com.example.board.entity.Reply;

@SpringBootTest
public class BoardRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void listReplyTest() {
        Board board = Board.builder().bno(98L).build();
        List<Reply> list = replyRepository.findByBoardOrderByRno(board);

        System.out.println(list);

    }

    @Test
    public void insertMemberTest() {

        IntStream.rangeClosed(1, 10).forEach(i -> {
            Member member = Member.builder()
                    .email("user" + i + "@gmail.com")
                    .password("1111")
                    .name("USER" + i)
                    .build();
            memberRepository.save(member);
        });
    }

    @Test
    public void insertBoardTest() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            int num = (int) (Math.random() * 10) + 1;
            Member member = Member.builder().email("user" + num + "@gmail.com").build();

            Board board = Board.builder()
                    .title("title" + i)
                    .content("content" + i)
                    .member(member)
                    .build();
            boardRepository.save(board);
        });
    }

    @Test
    public void insertReplyTest() {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            long no = (int) (Math.random() * 100) + 1;
            Board board = Board.builder().bno(no).build();

            Reply reply = Reply.builder()
                    .text("Reply...." + i)
                    .replyer("guest" + i)
                    .board(board)
                    .build();
            replyRepository.save(reply);
        });
    }

    @Test
    public void readBoardTest() {
        Board board = boardRepository.findById(2L).get();
        System.out.println(board);
    }

    @Transactional
    @Test
    public void readBoardTest2() {
        Board board = boardRepository.findById(2L).get();
        System.out.println(board.getMember());
    }

    @Transactional
    @Test
    public void readBoardTest3() {
        Board board = boardRepository.findById(6L).get();
        System.out.println(board.getMember());
        System.out.println(board.getReplies());
    }

    @Transactional
    @Test
    public void readReplyTest() {
        Reply reply = replyRepository.findById(2L).get();
        System.out.println(reply);
        System.out.println(reply.getBoard());
    }

    // querydsltest
    @Test
    public void listTest() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(0)
                .size(10)
                .type("tc")
                .keyword("title")
                .build();
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(),
                Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.list(pageRequestDTO.getType(), pageRequestDTO.getKeyword(), pageable);

        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }

    @Test
    public void rowTest() {
        Object[] result = boardRepository.getBoardByBno(1L);
        System.out.println(Arrays.toString(result));
    }

}
