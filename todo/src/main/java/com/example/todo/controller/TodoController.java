package com.example.todo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.example.todo.dto.ToDoDTO;
import com.example.todo.service.TodoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/list")
    public void getList(@RequestParam(defaultValue = "0") boolean completed, Model model) {
        log.info("전체 todo 가져오기 {}", completed);
        List<ToDoDTO> todos = todoService.list(completed);
        model.addAttribute("todos", todos); // 화면단으로 보내기위해서는 일반컨트롤러에서는 return불가능하기에 model에 담아야 함

        model.addAttribute("completed", completed); // 어떤(완료or미완료) 목록을 보여주는가?
    }

    @GetMapping("/read")
    public void getRead(Long id, Model model) {
        log.info("조회 {}", id);

        ToDoDTO tododto = todoService.read(id);
        model.addAttribute("tododto", tododto);
    }

    @PostMapping("/modify")
    public String postCompleted(ToDoDTO tododto, RedirectAttributes rttr) {
        log.info("수정 {}", tododto);

        Long id = todoService.changeCompleted(tododto);

        rttr.addAttribute("id", id); // redirect 할 때 id값을 같이 보냄
        return "redirect:/todo/read";
    }

    @GetMapping("/remove")
    public String getRemove(Long id) {
        log.info("삭제 {}", id);

        todoService.remove(id);

        return "redirect:/todo/list";
    }

    @GetMapping("/create")
    public void getCreate() {
        log.info("todo 작성 폼 요청");
    }

    @PostMapping("/create")
    public String postCreate(ToDoDTO tododto, RedirectAttributes rttr) {
        log.info("새로운 todo 입력");

        Long id = todoService.create(tododto);

        // read로 이동
        rttr.addAttribute("id", id);
        return "redirect:/todo/read";
    }

}
