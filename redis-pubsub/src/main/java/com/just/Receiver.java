package com.just;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author andyxu
 * @version V1.0
 * @Date 2020/9/16 20:07
 * @since
 */
@Slf4j
public class Receiver {
    private AtomicInteger counter = new AtomicInteger();

    public void receiveMessage(String message) {

        log.info("Received <" + message + ">");
        counter.incrementAndGet();
    }

    public int getCount() {
        return counter.get();
    }
}
