package com.emergency.web.mq.core;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.util.ReflectionUtils;
import com.emergency.web.mq.RedisListener;
import com.emergency.web.mq.RedisListenerMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 注册redis消息队列处理方法，@RedisListener注解扫描器
 */
public class RedisListenerAnnotationScanPostProcessor implements BeanPostProcessor {
    @Getter
    private static final List<RedisListenerMethod> redisListenerMethods = new ArrayList<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = bean.getClass();
        Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz);
        for (Method method : methods) {
            AnnotationAttributes annotationAttributes = AnnotatedElementUtils
                    .findMergedAnnotationAttributes(method, RedisListener.class, false, false);
            if (null != annotationAttributes) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1) {
                    String queueName = (String) annotationAttributes.get("queueName");
                    String topicName = (String) annotationAttributes.get("topicName");
                    if (StringUtils.isEmpty(queueName)||StringUtils.isEmpty(topicName)) {
                        throw new RuntimeException("在" + method + "方法上的注解@RedisListener没有设置参数queueName或者topicName的值");
                    }
                    RedisListenerMethod rlm = new RedisListenerMethod();
                    rlm.setBeanName(beanName);
                    rlm.setQueueName(queueName);
                    rlm.setTopicName(topicName);
                    rlm.setTargetMethod(method);
                    rlm.setMethodParameterClassName(parameterTypes[0].getName());
                    redisListenerMethods.add(rlm);
                } else {
                    throw new RuntimeException("有@RedisListener注解的方法有且仅能包含一个参数");
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
