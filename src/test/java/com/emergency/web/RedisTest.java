package com.emergency.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import com.emergency.web.mq.RedisMessage;
import com.emergency.web.utils.JsonUtils;

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
        redisMessage.setTopic("cancel");
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
        String str = """
                {
                  "id":"09194567",
                  "studentName":"王三",
                  "nickName": null,
                  "englishName":"King Three",
                  "age":32,
                  "email":"123@qq.com",
                  "birthday":"1989-12-21",
                  "joinDate":"2019-03-10 11:15:39",
                  "courseScores":[
                    {
                      "course":"Java",
                      "score":95
                    },
                    {
                      "course":"C#",
                      "score":94
                    },
                    {
                      "course":"C++",
                      "score":89
                    }
                  ],
                  "courseScoresGroup":{
                    "A":[
                      "Java",
                      "C#"
                    ],
                    "B":[
                      "C++"
                    ]
                  },
                  "valid":true
                }
                                
                """;


    }

}
