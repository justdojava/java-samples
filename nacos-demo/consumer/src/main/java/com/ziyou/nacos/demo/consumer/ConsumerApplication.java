package com.ziyou.nacos.demo.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * <br>
 * <b>Function：</b><br>
 * <b>Author：</b>@author Silence<br>
 * <b>Date：</b>2021-04-11 17:07<br>
 * <b>Desc：</b>无<br>
 */
@SpringBootApplication(scanBasePackages = "com.ziyou.nacos")
@EnableFeignClients(basePackages = {"com.ziyou.nacos.demo.consumer.rpc"})
@EnableCaching
public class ConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }
}
