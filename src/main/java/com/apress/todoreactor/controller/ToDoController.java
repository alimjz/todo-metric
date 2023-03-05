package com.apress.todoreactor.controller;

import com.apress.todoreactor.domain.ToDo;
import com.apress.todoreactor.repository.ToDoRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class ToDoController {

    private final ToDoRepository repository;

    public ToDoController(ToDoRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/todo/{id}")
    public Mono<ToDo> getToDo(String id){
        return this.repository.findById(id);
    }


    @GetMapping("/todo")
    public Flux<ToDo> getToDos(){
        return this.repository.findAll();
    }
}
