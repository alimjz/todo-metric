package com.apress.todoreactor.repository;

import com.apress.todoreactor.domain.ToDo;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
@Repository
public class ToDoRepository {
    private final Flux<ToDo> toDoFlux =
            Flux.fromIterable(Arrays.asList(
                    new ToDo("Do Home work!"),
                    new ToDo("Do rest",true),
                    new ToDo("make dinner",true)));

    public Mono<ToDo> findById(String id){
        return Mono.from(toDoFlux.filter(toDo -> toDo.getId().equals(id)));
    }

    public Flux<ToDo> findAll(){
        return toDoFlux;
    }
}
