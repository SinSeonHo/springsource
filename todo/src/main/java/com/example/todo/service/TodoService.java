package com.example.todo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.example.todo.dto.ToDoDTO;
import com.example.todo.entity.ToDo;
import com.example.todo.repository.TodoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class TodoService {

    private final TodoRepository todoRepository;

    private final ModelMapper modelMapper;

    public Long create(ToDoDTO tododto) {

        ToDo todo = modelMapper.map(tododto, ToDo.class);
        return todoRepository.save(todo).getId();

    }

    public void remove(Long id) {
        todoRepository.deleteById(id);
    }

    public ToDoDTO read(Long id) {
        ToDo todo = todoRepository.findById(id).get();
        // entity => dto로 변경 후 리턴
        return modelMapper.map(todo, ToDoDTO.class);
    }

    public Long changeCompleted(ToDoDTO tododto) {
        ToDo todo = todoRepository.findById(tododto.getId()).get();
        todo.setCompleted(tododto.isCompleted());
        return todoRepository.save(todo).getId();

    }

    public List<ToDoDTO> list(boolean completed) {

        List<ToDo> list = todoRepository.findByCompleted(completed);
        // ToDo entity => ToDoDTO 변경 후 리턴

        // List<ToDoDto> todos = new ArrayList<>();
        // list.forEach(todo -> {
        // ToDoDto dto = modelMapper.map(todo, ToDoDto.class);
        // todos.add(dto);
        // });

        List<ToDoDTO> todos = list.stream().map(todo -> modelMapper.map(todo, ToDoDTO.class))
                .collect(Collectors.toList());

        return todos;

    }
}
