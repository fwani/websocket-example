package pe.fwani.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pe.fwani.model.*;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
public class AController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws InterruptedException {
        Thread.sleep(1000);
        return new Greeting("Hello, " + message.getName() + "!");
    }

    @PostMapping("/api/query")
    public JobId requestQuery(@RequestBody QueryMessage message) {
        log.info("Query: " + message.getQ());
        if (message.getQ().equals("123")) {
            return new JobId("123");
        } else {
            return new JobId("111");
        }
    }

    @MessageMapping("/query")
    @SendTo("/topic/query")
    public QueryResult query(JobId message) throws InterruptedException {
        log.info("JobId: " + message.getJobId());
        Thread.sleep(1000);
        if (Objects.equals(message.getJobId(), "123")) {
            return new QueryResult(message.getJobId(),
                    List.of(new Field("A", "TEXT")), List.of());
        } else {
            return new QueryResult(message.getJobId(),
                    List.of(
                    new Field("A", "TEXT"),
                    new Field("B", "TEXT")
            ), List.of(
                    List.of("a", "b")
            ));
        }
    }

}
