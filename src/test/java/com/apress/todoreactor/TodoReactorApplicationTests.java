package com.apress.todoreactor;

import com.apress.todoreactor.domain.ToDo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@JsonTest
class TodoReactorApplicationTests {

    @Autowired
    private JacksonTester<ToDo> json;

    private final Flux<ToDo> toDoFlux =
            Flux.fromIterable(Arrays.asList(
                    new ToDo("Do Home work!"),
                    new ToDo("Do rest",true),
                    new ToDo("make dinner",true)));
    @Test
    public void toDoTest(){
        String result = new TestRestTemplate().getForObject("http://localhost:8080/todo", String.class);
        Assertions.assertThat(result).contains("Do Home work!");
    }
    @Test
    public void jsonSerialize() throws IOException {
        ToDo readBook = new ToDo("Read book");
        String jsonTodo = "{\"description\":\"Read book\",\"completed\": false }";
        Assertions.assertThat(this.json.write(readBook)).isEqualToJson(jsonTodo);
    }



}
