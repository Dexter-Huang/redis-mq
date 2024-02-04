package com.emergency.web.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;

/**
 * @author xian
 * @date 05/10/2019 10:31
 */
@Slf4j
public class JsonUtils {

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_NULL);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(dateFormat);
    }

    public static ObjectNode parseObject(String json) {
        try {
            return objectMapper.readValue(json, ObjectNode.class);
        } catch (JsonProcessingException e) {
            log.error("[parseObject]parse json string error:" + json, e);
            return null;
        }
    }

    public static String toJson(Object object) {
        if (object == null) {
            return null;
        }
        try {
            if (null == objectMapper) {
                objectMapper = new ObjectMapper();
                objectMapper.setSerializationInclusion(Include.NON_NULL);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                objectMapper.setDateFormat(dateFormat);
            }
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("[toJson] error:", e);
        }
        return null;
    }

    public static <T> T fromJson(InputStream inputStream, JavaType javaType) {
        if (inputStream == null) {
            return null;
        }
        try {
            return objectMapper.readValue(inputStream, javaType);
        } catch (IOException e) {
            log.error("[fromJson] error", e);
        }
        return null;
    }

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (StringUtils.isBlank(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            log.error("[fromJson]json转换异常json:{}", json, e);
            return null;
        }
    }

    public static <T> T fromJson(String json, JavaType javaType) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            log.error("parse json string error:" + json, e);
            return null;
        }
    }

    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (IOException e) {
            log.error("parse json string error:" + json, e);
            return null;
        }
    }


    public static JavaType buildCollectionType(Class<? extends Collection> collectionClass, Class<?> elementClass) {
        return objectMapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
    }

}

