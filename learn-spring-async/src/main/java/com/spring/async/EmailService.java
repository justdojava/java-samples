package com.spring.async;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author andyxu
 * @version V1.0
 * @Date 2020/6/28 19:11
 * @since
 */
@Slf4j
@Service
public class EmailService {



    /**
     * 异步发送任务
     *
     * @throws InterruptedException
     */
    @SneakyThrows
    @Async
    public void sendEmailAsync() {
        log.info("使用 Spring 异步任务发送邮件示例");
        TimeUnit.SECONDS.sleep(2l);
    }

    @SneakyThrows
    public void sendEmail() {
        log.info("使用线程池发送邮件示例");
        TimeUnit.SECONDS.sleep(2l);
    }



    @Async
    @SneakyThrows
    public Future<String> sendEmailAsyncWithResult() {
        log.info("使用 Spring 异步任务发送邮件，并且获取任务返回结果示例");
        TimeUnit.SECONDS.sleep(2l);
        return AsyncResult.forValue("success");
    }

    @Async
    @SneakyThrows
    public Future<String> sendEmailAsyncWithException() {
        log.info("使用 Spring 异步任务发送邮件，并且获取任务返回结果示例");
        int error = 11 / 0;
        TimeUnit.SECONDS.sleep(2l);
        return AsyncResult.forValue("success");
    }




    @Async
    public void sendEmailWithError() {
        log.info("使用 Spring 异步任务发送邮件，方法内异常示例");
        throw new RuntimeException("模拟异常");
    }


    @Async
    @SneakyThrows
    public ListenableFuture<String> sendEmailAsyncWithListenableFuture() {
        log.info("使用 Spring 异步任务发送邮件，并且获取任务返回结果示例");
        TimeUnit.SECONDS.sleep(2l);
        return AsyncResult.forValue("success");
    }

}
