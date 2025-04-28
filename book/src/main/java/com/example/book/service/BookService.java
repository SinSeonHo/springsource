package com.example.book.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.book.dto.BookDTO;
import com.example.book.entity.Book;
import com.example.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    public Long insert(BookDTO dto) {
        // dto => entity 바꿔야됨
        Book book = modelMapper.map(dto, Book.class);
        return bookRepository.save(book).getCode();

    }

    public BookDTO read(Long code) {
        Book book = bookRepository.findById(code).get();
        // entity => dto 바꿔야됨
        return modelMapper.map(book, BookDTO.class);
    }

    public List<BookDTO> readAll() {
        List<Book> booklist = bookRepository.findAll();
        // entity => dto 바꿔야됨
        List<BookDTO> books = booklist.stream().map(book -> modelMapper.map(book, BookDTO.class))
                .collect(Collectors.toList());
        return books;
    }

    public Book modify(BookDTO dto) {
        Book book = bookRepository.findById(dto.getCode()).get();
        book.setPrice(dto.getPrice());
        return bookRepository.save(book);
    }

    public void remove(Long code) {
        bookRepository.deleteById(code);

    }
}
