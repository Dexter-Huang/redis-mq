package top.aolien.redis.consumer;

import org.springframework.stereotype.Component;
import top.aolien.redis.mq.RedisListener;
@Component
public class TestConsumer {
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
