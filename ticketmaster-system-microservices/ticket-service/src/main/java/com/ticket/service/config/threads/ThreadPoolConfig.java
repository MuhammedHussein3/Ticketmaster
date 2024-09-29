package com.ticket.service.config.threads;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class ThreadPoolConfig implements AsyncConfigurer {


    @Autowired
    private DefaultAsyncUncaugtExceptionHandler defaultAsyncUncaugtExceptionHandler;


    @Bean(name = "myThreadPoolExecutor")
    public Executor taskPoolExecutor(){

        int minPoolSize = 7;
        int maxPoolSize = 8;
        int queueSize = 10;

        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();

        threadPoolTaskExecutor.setCorePoolSize(minPoolSize);
        threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
        threadPoolTaskExecutor.setQueueCapacity(queueSize);
        threadPoolTaskExecutor.setThreadNamePrefix("MyThread");
        threadPoolTaskExecutor.setRejectedExecutionHandler(new RejectedDefaultHandler());
        threadPoolTaskExecutor.setThreadFactory(new TicketThreadFactory());
        threadPoolTaskExecutor.initialize();

        return threadPoolTaskExecutor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler(){
        return this.defaultAsyncUncaugtExceptionHandler;
    }
}
