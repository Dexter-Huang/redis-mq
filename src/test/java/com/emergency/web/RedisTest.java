package com.emergency.web;

import com.emergency.web.enums.RedisConstKeys;
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
        String str = """
                {"topic":"START_CALL","data":{"aidId":"415","rescueeAccount":"17841042461","rescueeName":"黄明朗","patientName":"","autoAddr":"广东省阳江市阳春市松竹路","rescueTime":"2024-02-05 00:21:31","type":1,"latitude":22.158151,"longitude":111.79792}}
                """;
        stringRedisTemplate.opsForList().rightPush(RedisConstKeys.EMERGENCY_KEY2, str);
    }

    @Test
    public void testEquals(){
        String str = """
                {"topic":"CANCEL_CALL","data":{"aidId":"416","rescuerAccount":"","rescuerType":0,"is_success":false}}        
                """;
        RedisMessage o = JsonUtils.fromJson(str, RedisMessage.class);
        System.out.println(o.getTopic());
        System.out.println(o.getData());

    }

}
