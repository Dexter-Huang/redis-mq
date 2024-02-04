package top.aolien.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import top.aolien.redis.mq.RedisListener;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @RedisListener(queueName = "test", topicName = "start_call")
    public void test(String message) {
        System.out.println("Start call: ");
        System.out.println("test: " + message);
    }

    @RedisListener(queueName = "test", topicName = "cancel")
    public void test2(String message) {
        System.out.println("Cancel: ");
        System.out.println("test: " + message);
    }



}
