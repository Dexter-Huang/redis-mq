package top.aolien.redis.mq;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.aolien.redis.mq.core.RedisListenerAnnotationScanPostProcessor;
import top.aolien.redis.mq.core.RedisMessageQueueRegister;


@Configuration
@ConditionalOnProperty(prefix = "redis.queue.listener", name = "enable", havingValue = "true", matchIfMissing = true)
public class RedisMQListenerAutoConfig {

    @Bean
    public RedisListenerAnnotationScanPostProcessor redisListenerAnnotationScanPostProcessor(){
        return new RedisListenerAnnotationScanPostProcessor();
    }

    @Bean
    public RedisMessageQueueRegister redisMessageQueueRegister(){
        return new RedisMessageQueueRegister();
    }
}
