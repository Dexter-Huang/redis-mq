package com.emergency.web;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

public class JacksonTest {
    @Test
    public void testJackson() {
        String str = """
                {"queueName":"test","topicName":"cancel","data":"test-data"}
                """;
        JSONObject jsonObject = JSONObject.parseObject(str);
        System.out.println(jsonObject);
    }
}
