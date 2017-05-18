package com.xiao.CompletableFutureDemo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Description: 结合（链接）两个futures, thenCompose会按照顺序执行
 * User: xiaojixiang
 * Date: 2017/4/27
 * Version: 1.0
 */

public class Test3 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    System.out.println(Thread.currentThread().getName()+">>>futureA start");
                    Thread.sleep(1000 * 2);
                    System.out.println(Thread.currentThread().getName()+">>>futureA end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello";
            }
        });


        CompletableFuture<String> futureB = futureA.thenCompose(new Function<String, CompletionStage<String>>() {
            @Override
            public CompletionStage<String> apply(String s) {
                return CompletableFuture.supplyAsync(new Supplier<String>() {
                    @Override
                    public String get() {
                        System.out.println(Thread.currentThread().getName()+">>>futureB start");
                        return s+" world";
                    }
                });
            }
        });

        CompletableFuture<User> futureC = futureB.thenCompose(new Function<String, CompletionStage<User>>() {
            @Override
            public CompletionStage<User> apply(String s) {
                return CompletableFuture.supplyAsync(new Supplier<User>() {
                    @Override
                    public User get() {
                        System.out.println(Thread.currentThread().getName()+">>>futureC start");
                        User a = new User();
                        a.setUsername(s);
                        System.out.println(Thread.currentThread().getName()+">>>futureC end");
                        return a;
                    }
                });
            }
        });

        System.out.println(futureC.get().toString());

    }

}
