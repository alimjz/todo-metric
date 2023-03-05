package com.apress.todoreactor;

import com.apress.todoreactor.domain.ToDo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.metrics.ApplicationStartup;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

@SpringBootApplication
public class TodoReactorApplication {
    public static final Logger log = LoggerFactory.getLogger(TodoReactorApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(TodoReactorApplication.class,args);
    }
//    @Bean
    public CommandLineRunner runExample(){
        return args -> {
            MonoProcessor<ToDo> processor = MonoProcessor.create();
            Mono<ToDo> result = processor.doOnSuccess(p-> log.info("Mono >> ToDo: {}",p.getDescription()))
                    .doOnTerminate(()-> log.info("Mono >> Done"))
                    .doOnError(t-> log.info(t.getMessage(),t))
                    .subscribeOn(Schedulers.single());

            processor.onNext(new ToDo("Buy my ticker for spring one platform 2018"));
//            processor.onError(new IllegalArgumentException("There is an error processing the todo ..."));
            result.block(Duration.ofMillis(1000));
        };
    }

}
