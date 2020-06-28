package com.spring.async;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

/**
 * @author andyxu
 * @version V1.0
 * @Date 2020/6/28 19:10
 * @since
 */
@Slf4j
@RestController
public class RegisterController {


    @Autowired
    EmailService emailService;


    @RequestMapping("register")
    public String register() {
        log.info("注册流程开始");
        // sendEmailByThreadPool();
       // sendEmailWithSpringAsync();
//        sendEmailWithResult();
//        sendEmailWithError();

        sendEmailAsyncWithListenableFuture();


        return "success";
    }

    // 生产使用线程池的最佳实践，一定要自定义线程池，不要嫌麻烦，使用 Executors 创建线程池
    private ThreadPoolExecutor threadPool =
            new ThreadPoolExecutor(5,
                    10,
                    60l,
                    TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(200),
                    new ThreadFactoryBuilder().setNameFormat("register-%d").build());

    /**
     * 使用线程池执行发送邮件的任务
     */
    private void sendEmailByThreadPool() {

        threadPool.submit(() -> emailService.sendEmail());
    }


    private void sendEmailWithResult() {
        Future<String> future = emailService.sendEmailAsyncWithResult();
        try {
            String result = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private void sendEmailWithError() {
        emailService.sendEmailWithError();
        try {
            Future<String> future = emailService.sendEmailAsyncWithException();
            String result = future.get();
        } catch (RuntimeException e) {
            log.error("捕获模拟异常信息", e);
        } catch (Exception e) {
            log.error("其他错误", e);
        }

    }

    private void sendEmailWithSpringAsync() {
        emailService.sendEmailAsync();
    }

    private void sendEmailAsyncWithListenableFuture() {
        ListenableFuture<String> listenableFuture = emailService.sendEmailAsyncWithListenableFuture();
        // 异步回调处理
        listenableFuture.addCallback(new SuccessCallback<String>() {
            @Override
            public void onSuccess(String result) {
                log.info("异步回调处理返回值");

            }
        }, new FailureCallback() {
            @Override
            public void onFailure(Throwable ex) {
                log.error("异步回调处理异常",ex);
            }
        });
    }


}
