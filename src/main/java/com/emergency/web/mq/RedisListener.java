package com.emergency.web.mq;

import java.lang.annotation.*;

/**
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisListener {
    String queueName() default "";
    String topicName() default "";

}
