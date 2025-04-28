package com.example.book.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.book.entity.Book;

@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void testInsert() {
        // 20권 책 넣기
        IntStream.rangeClosed(1, 20).forEach(i -> {
            Book book = Book.builder()
                    .title("Book Title" + i)
                    .author("author" + i)
                    .price(10000 * i)
                    .build();
            bookRepository.save(book);
        });
    }

    @Test
    public void testList() {
        // 전체 책 리스트 보여주기
        bookRepository.findAll().forEach(book -> System.out.println(book));
    }

    @Test
    public void testGet() {
        // id 지정하여 1개의 책 정보 보여주기
        Book book = bookRepository.findById(25L).get();
        System.out.println("책 정보 : " + book);
    }

    @Test
    public void testUpdate() {
        // 가격 수정
        Book book = bookRepository.findById(25L).get();
        book.setPrice(19000);
        bookRepository.save(book);
    }

    @Test
    public void testRemove() {
        // 삭제
        bookRepository.deleteById(24L);
    }

}
