package com.apress.todoreactor.example;

import com.apress.todoreactor.domain.ToDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

//@Configuration
public class FluxExample {
    public static final Logger log = LoggerFactory.getLogger(FluxExample.class);
//    @Bean
    public CommandLineRunner runFluxExample(){
        return args -> {
            EmitterProcessor<ToDo> stream = EmitterProcessor.create();
            Mono<List<ToDo>> promise = stream.filter(s-> s.isCompleted())
                    .doOnNext(s-> log.info("Flux >>> Todo: {}", s.getDescription()))
                    .collectList()
                    .subscribeOn(Schedulers.single());
            stream.onNext(new ToDo("read a book",true));
            stream.onNext(new ToDo("listen to music",true));
            stream.onNext(new ToDo("workout"));
            stream.onNext(new ToDo("clean desk", true));
            stream.onNext(new ToDo("sp1 2018 is coming", true));
            stream.onComplete();
            promise.block();
        };
    }
}
