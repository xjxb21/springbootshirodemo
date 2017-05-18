package com.xiao.lettuceDemo;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisFuture;
import com.lambdaworks.redis.api.async.RedisAsyncCommands;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Description:
 * User: xiaojixiang
 * Date: 2017/5/2
 * Version: 1.0
 */

public class Demo1 {

    public static void main(String[] args) throws InterruptedException {
        RedisClient redisClient = RedisClient.create("redis://192.168.8.101");

        //RedisCommands<String, String> redisCommands = redisClient.connect().sync();
        //redisCommands.set("hello", "world");
        //System.out.println(redisCommands.get("hello"));

        RedisAsyncCommands<String, String> redisCommands = redisClient.connect().async();
        RedisFuture<String> future = redisCommands.get("hello");

        future.handleAsync(new BiFunction<String, Throwable, Object>() {
            @Override
            public Object apply(String s, Throwable throwable) {
                //netty nioEventLoop中的线程执行
                System.out.println(Thread.currentThread().getName() + ">>>>>>>>>>>>>>handle:" + s);
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                return null;
            }
        });
        future.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                System.out.println(Thread.currentThread().getName() + ">>>>>>>>>>>>>>whenComplete:" + s);
            }
        });

        Thread.sleep(Integer.MAX_VALUE);
    }
}
