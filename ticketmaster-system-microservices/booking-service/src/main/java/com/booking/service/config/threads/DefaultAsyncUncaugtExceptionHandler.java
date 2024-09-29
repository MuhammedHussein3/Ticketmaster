package com.booking.service.config.threads;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class DefaultAsyncUncaugtExceptionHandler extends Throwable implements AsyncUncaughtExceptionHandler {
    @Override
    public void handleUncaughtException(Throwable ex, Method method, Object... params) {
        System.out.println("in default Uncaught exception handler");
    }
}

