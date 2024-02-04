package com.emergency.web.consumer;

import com.emergency.web.enums.RedisConstKeys;
import com.emergency.web.mq.RedisMessage;
import org.springframework.stereotype.Component;
import com.emergency.web.mq.RedisListener;
@Component
public class RescueMessageConsumer {
    @RedisListener(queueName = RedisConstKeys.EMERGENCY_KEY2, topicName = RedisConstKeys.START_CALL)
    public void StartCall(RedisMessage message) {
        System.out.println("Start call: ");
        System.out.println("test: " + message);
    }

    @RedisListener(queueName = RedisConstKeys.EMERGENCY_KEY2, topicName = RedisConstKeys.CANCEL_CALL)
    public void CancelCall(RedisMessage message) {
        System.out.println("Cancel: ");
        System.out.println("test: " + message);
    }
}
