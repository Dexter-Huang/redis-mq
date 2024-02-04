package com.emergency.web.mq;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;

@Setter
public class RedisListenerMethod {
    @Getter
    private String queueName;
    @Getter
    private String topicName;

    private volatile Object bean;

    public Object getBean(ApplicationContext applicationContext) {
        if (bean == null) {
            synchronized (this) {
                if (bean == null) {
                    bean = applicationContext.getBean(beanName);
                    if (bean == null) {
                        throw new RuntimeException("获取包含@RedisLister[" + targetMethod.getName() + "]方法的Bean实例失败");
                    }
                }
            }
        }
        return bean;
    }

    @Getter
    private String beanName;

    @Getter
    private Method targetMethod;
    @Getter
    private String methodParameterClassName;

    public boolean match(String queueName) {
        return StringUtils.equals(this.queueName, queueName);
    }
}
