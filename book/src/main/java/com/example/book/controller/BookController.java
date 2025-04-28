package com.example.book.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.book.dto.BookDTO;

import com.example.book.service.BookService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Log4j2
@RequiredArgsConstructor // final있으면 무조건 선언해야됨
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    @GetMapping("/create")
    public void getCreate(@ModelAttribute("book") BookDTO dto) {
        log.info("도서 작성 폼 요청");
    }

    // @ModelAttribute("book") BookDTO별칭 붙이기
    // @Valid BookDTO dto, BindingResult result 발리데이션 기본코드
    @PostMapping("/create")
    public String postCreate(@ModelAttribute("book") @Valid BookDTO dto, BindingResult result,
            RedirectAttributes rttr) {
        log.info("도서 작성 폼 요청");

        if (result.hasErrors()) {
            return "/book/create";
        }
        // 서비스 호출
        Long code = bookService.insert(dto);

        // rttr.addAttribute(code, 2030); -> ?code=2030 경로에 이렇게 따라붙음 / 화면단 ${param.code}
        // rttr.addFlashAttribute("code", code); -> 세션을 이용하는 개념이기에 주소줄에 따라가지않음 / ${code}
        rttr.addFlashAttribute("code", code);
        return "redirect:/book/list";
    }

    @GetMapping("/list")
    public void getList(Model model) {
        log.info("book list 요청");
        List<BookDTO> books = bookService.readAll();
        model.addAttribute("books", books);
    }

    // http://localhost:8080/book/read?code=31
    // http://localhost:8080/book/modify?code=31
    // 둘다 응답함
    // 수정 : get 한 뒤 수정할 내용 보여주기
    // : post 실제 수정할 내용 가져와 서비스로 보내주기
    @GetMapping({ "/read", "/modify" })
    public void getRead(Long code, Model model) {
        log.info("book get 요청 {}", code);
        BookDTO bookDTO = bookService.read(code);
        model.addAttribute("book", bookDTO);
    }

    @PostMapping("/modify")
    public String postModify(BookDTO dto, RedirectAttributes rttr) {
        log.info("book modify 요청 {}", dto);
        // Service단에서 modify메소드 호출
        bookService.modify(dto);
        // read로 돌아가기
        rttr.addAttribute("code", dto.getCode()); // redirect 할 때 price값을 같이 보냄
        return "redirect:/book/read";
    }

    @PostMapping("/remove")
    public String postRemove(Long code) {
        log.info("book remove 요청 {}", code);

        // 서비스 호출
        bookService.remove(code);
        return "redirect:/book/list";
    }

}
