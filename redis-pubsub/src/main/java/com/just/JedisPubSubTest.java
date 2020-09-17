package com.just;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

/**
 * @author andyXu xu9529@gmail.com
 * @date 2020/9/17
 */
public class JedisPubSubTest {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.auth("1234qwer");
        jedis.subscribe(new MyListener(), "pay_result");


    }


    private static class MyListener extends JedisPubSub {
        @Override
        public void onMessage(String channel, String message) {
            System.out.println("收到订阅频道：" + channel + " 消息：" + message);

        }

        @Override
        public void onPMessage(String pattern, String channel, String message) {
            System.out.println("收到具体订阅频道：" + channel + "订阅模式：" + pattern + " 消息：" + message);
        }

    }
}
