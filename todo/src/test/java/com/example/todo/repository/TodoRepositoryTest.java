package com.example.todo.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.todo.entity.ToDo;

@SpringBootTest
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    // todo 데이터삽입
    @Test
    public void todoInsert() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            ToDo todo = ToDo.builder()
                    .content("강아지 산책 " + i)
                    .build();
            todoRepository.save(todo);
        });
    }

    // todo 전체조회
    @Test
    public void todoAllRead() {
        todoRepository.findAll().forEach(todo -> System.out.println(todo));
    }

    // 완료 목록 추출
    @Test
    public void todoAllRead2() {
        todoRepository.findByCompleted(true).forEach(todo -> System.out.println(todo));
    }

    // 안중요 목록 추출
    @Test
    public void todoAllRead3() {
        todoRepository.findByImportanted(false).forEach(todo -> System.out.println(todo));
    }

    // todo 삭제
    @Test
    public void todoDelete() {
        todoRepository.deleteById(1L);
    }

    // todo 수정 - 완료 (0 -> 1)
    @Test
    public void todoUpdate() {
        ToDo todo = todoRepository.findById(3L).get();
        todo.setCompleted(true);
        todoRepository.save(todo);
    }
}
