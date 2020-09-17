package com.just;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author andyXu xu9529@gmail.com
 * @date 2020/9/17
 */
@SpringBootApplication
public class MessagingRedisApplication {
    public static void main(String[] args) throws InterruptedException {

        ApplicationContext ctx = SpringApplication.run(MessagingRedisApplication.class, args);

        StringRedisTemplate template = ctx.getBean(StringRedisTemplate.class);
        Receiver receiver = ctx.getBean(Receiver.class);

        while (receiver.getCount() == 0) {
            template.convertAndSend("pay_result", "Hello from Redis!");
            Thread.sleep(500L);
        }

        System.exit(0);
    }
}
