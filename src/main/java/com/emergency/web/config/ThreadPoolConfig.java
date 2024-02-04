package com.emergency.web.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
public class ThreadPoolConfig {
    @Value("${spring.task.execution.pool.core-size}")
    private int corePoolSize;
    @Value("${spring.task.execution.pool.max-size}")
    private int maxPoolSize;
    @Value("${spring.task.execution.pool.queue-capacity}")
    private int queueCapacity;
    @Value("${spring.task.execution.pool.keep-alive}")
    private int keepAliveSeconds;
    @Value("${spring.task.execution.shutdown.await-termination-period}")
    private int awaitTerminationSeconds;
    @Value("${spring.task.execution.thread-name-prefix.default}")
    private String defaultDispatchThreadNamePrefix;
    @Value("${spring.task.execution.thread-name-prefix.rescuer}")
    private String rescuerDispatchThreadNamePrefix;
    @Value("${spring.task.execution.thread-name-prefix.volunteer}")
    private String volunteerDispatchThreadNamePrefix;

    private ThreadPoolTaskExecutor getThreadPoolTaskExecutor(String defaultDispatchThreadNamePrefix) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setKeepAliveSeconds(keepAliveSeconds);
        executor.setThreadNamePrefix(defaultDispatchThreadNamePrefix);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        return executor;
    }

    @Bean(name = "defaultThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor defaultThreadPoolTaskExecutor(){
        return getThreadPoolTaskExecutor(defaultDispatchThreadNamePrefix);
    }

    @Bean(name = "rescuerThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor rescuerThreadPoolTaskExecutor(){
        return getThreadPoolTaskExecutor(rescuerDispatchThreadNamePrefix);
    }

    @Bean(name = "volunteerThreadPoolTaskExecutor")
    public ThreadPoolTaskExecutor volunteerThreadPoolTaskExecutor(){
        return getThreadPoolTaskExecutor(volunteerDispatchThreadNamePrefix);
    }


}
