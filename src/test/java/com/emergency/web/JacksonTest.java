package com.emergency.web;

import com.alibaba.fastjson.JSONObject;
import com.emergency.web.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

    @Test
    public void testEquals(){
        String str = """
                {"queueName":"test","topicName":"cancel","data":"test-data"}
                """;
        System.out.println(JsonUtils.parseObject(str));;
    }
}
