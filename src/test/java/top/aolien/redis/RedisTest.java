package top.aolien.redis;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import top.aolien.redis.mq.RedisMessage;
import top.aolien.redis.mq.utils.JsonUtils;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testPop(){
        RedisMessage<String> redisMessage = new RedisMessage<>();
        redisMessage.setQueueName("test");
        redisMessage.setData("test-data");
        redisMessage.setTopicName("cancel");
        stringRedisTemplate.opsForList().rightPush("test", JsonUtils.toJson(redisMessage));
    }

    @Test
    public void testList(){
        RedisMessage<String> redisMessage = new RedisMessage<>();
        redisMessage.setQueueName("test");
        redisMessage.setData("test-data");
        System.out.println(redisMessage.toString());
        System.out.println(JsonUtils.toJson(redisMessage));
        System.out.println(JsonUtils.fromJson(JsonUtils.toJson(redisMessage), RedisMessage.class));
    }

    @Test
    public void testEquals(){
        System.out.println(StringUtils.equals("test", "test"));;
        System.out.println(StringUtils.equals("test", null));;
        System.out.println(StringUtils.equals(null, null));;
    }

}
