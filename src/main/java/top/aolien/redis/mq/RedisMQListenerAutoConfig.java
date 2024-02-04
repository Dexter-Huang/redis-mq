package top.aolien.redis.mq;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import top.aolien.redis.mq.core.RedisListenerAnnotationScanPostProcessor;
import top.aolien.redis.mq.core.RedisMessageQueueRegister;

import javax.annotation.Resource;

@Configuration
@ConditionalOnBean(RedisConnectionFactory.class)
@ConditionalOnProperty(prefix = "redis.queue.listener", name = "enable", havingValue = "true", matchIfMissing = true)
public class RedisMQListenerAutoConfig {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisListenerAnnotationScanPostProcessor redisListenerAnnotationScanPostProcessor(){
        return new RedisListenerAnnotationScanPostProcessor();
    }

    @Bean
    public RedisMessageQueueRegister redisMessageQueueRegister(){
        return new RedisMessageQueueRegister();
    }
}
