package com.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;

/**
 * @author andyxu
 * @version V1.0
 * @Date 2020/6/28 19:45
 * @since
 */
@Slf4j
@Configuration
public class AsyncErrorHandler extends AsyncConfigurerSupport {

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        AsyncUncaughtExceptionHandler handler = (throwable, method, objects) -> {
            log.error("全局异常捕获", throwable);
        };
        return handler;
    }

}
