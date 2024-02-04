package com.emergency.web.mq.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import com.emergency.web.mq.RedisListenerMethod;
import com.emergency.web.mq.RedisMessage;
import com.emergency.web.utils.JsonUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisMessageQueueRegister implements ApplicationRunner, ApplicationContextAware {


    private final static String THREAD_PREFIX = "redismq-thread-";

    private final Set<String> registerQueueListener = new HashSet<>();

    private ApplicationContext applicationContext;

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Resource
    private ThreadPoolTaskExecutor defaultThreadPoolTaskExecutor;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void init() {
        List<RedisListenerMethod> candidates = RedisListenerAnnotationScanPostProcessor.getCandidates();
        for (RedisListenerMethod candidate : candidates) {
            registerQueueListener.add(candidate.getQueueName());
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 初始化消息队列名称
        init();
        // 启动redis消息队列监听器
        for (String listener : registerQueueListener) {
            Thread thread = new Thread(new Worker().setQueueName(listener));
            thread.setName(THREAD_PREFIX + listener);
            defaultThreadPoolTaskExecutor.execute(thread);
            log.info("启动消息队列监听器：【" + listener + "】");
        }
    }

    private class Worker implements Runnable{
        private String queueName = "";

        private List<RedisListenerMethod> canApplyList = new ArrayList<>();

        @Override
        public void run() {
            if (StringUtils.isEmpty(queueName)) {
                return;
            }

            while (true) {
                try {
                    String msg =  stringRedisTemplate.opsForList().leftPop(queueName, 30L, TimeUnit.SECONDS);
                    RedisMessage redisMessage = null;
                    if(msg == null) {
                        log.info("消息队列【" + queueName + "】没有消息");
                        continue;
                    } else {
                        redisMessage = JsonUtils.fromJson(msg, RedisMessage.class);
                        log.info("消息队列【" + queueName + "】接收到消息：" + msg);
                    }
                    checkRedisMessage(redisMessage);

                    if (!canApplyList.isEmpty()) {
                        for (RedisListenerMethod rlm : canApplyList) {
                            Method targetMethod = rlm.getTargetMethod();
                            if (rlm.getMethodParameterClassName().equals(RedisMessage.class.getName())
                                && rlm.getTopicName().equals(redisMessage.getTopic())) {
                                targetMethod.invoke(rlm.getBean(applicationContext), redisMessage);
                                break;
                            } else if (rlm.getMethodParameterClassName().equalsIgnoreCase(redisMessage.getData().getClass().getName())
                                && rlm.getTopicName().equals(redisMessage.getTopic())){
                                targetMethod.invoke(rlm.getBean(applicationContext), redisMessage.getData());
                                break;
                            }
                        }
                    }

                } catch (QueryTimeoutException e1) {
                    log.warn(e1.getMessage());
                } catch (Throwable e) {
                    log.error("redisMQ队列【" + queueName + "】消息处理时异常", e);
                }
            }
        }

        public void checkRedisMessage(RedisMessage redisMessage) {
            if (redisMessage == null) {
                log.error("redisMessage is null");
                throw new IllegalArgumentException("redisMessage is null");
            }
            if (StringUtils.isEmpty(redisMessage.getQueueName())) {
                log.error("queueName is null");
                throw new IllegalArgumentException("queueName is null");
            }
            if (StringUtils.isEmpty(redisMessage.getTopic())) {
                log.error("topicName is null");
                throw new IllegalArgumentException("topicName is null");
            }
        }
        private void obtainCanApplyList() {
            if (canApplyList.size() <= 0) {
                List<RedisListenerMethod> all = RedisListenerAnnotationScanPostProcessor.getCandidates();
                for (RedisListenerMethod rlm : all) {
                    if (rlm.match(queueName)) {
                        canApplyList.add(rlm);
                    }
                }
            }
        }

        public Worker setQueueName(String queueName) {
            this.queueName = queueName;
            obtainCanApplyList();
            return this;
        }
    }
}
