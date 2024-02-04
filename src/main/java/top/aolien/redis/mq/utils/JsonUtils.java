package top.aolien.redis.mq.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

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

    public static <T> T readSingle(String path, Type type) throws IOException {

        ClassPathResource resource = new ClassPathResource(path);
        if (resource.exists()) {
            return JSON.parseObject(resource.getInputStream(), StandardCharsets.UTF_8, type,
                    // 自动关闭流
                    Feature.AutoCloseSource,
                    // 允许注释
                    Feature.AllowComment,
                    // 允许单引号
                    Feature.AllowSingleQuotes,
                    // 使用 Big decimal
                    Feature.UseBigDecimal);
        } else {
            throw new IOException();
        }
    }

    public static <T> List<T> readArray(String path, Class<T> t) throws IOException {
        ClassPathResource resource = new ClassPathResource(path);
        if (!resource.exists()) {
            throw new IOException();
        }
        try (InputStream stream = resource.getInputStream()) {
            byte[] bytes = new byte[stream.available()];
            stream.read(bytes);
            return JSON.parseArray(new String(bytes), t);
        }
    }

    /**
     * 转集合
     *
     * @param value 字符串
     * @param clazz 需要转的类型
     * @param <T>   泛型
     * @return List<T>
     */
    public static <T> List<T> toList(String value, Class<T> clazz) {
        return fromJson(value, buildCollectionType(List.class, clazz));
    }

}

