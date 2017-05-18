package com.xiao.CompletableFutureDemo;

import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Description:allOf、 anyOf 辅助方法
 * User: xiaojixiang
 * Date: 2017/4/27
 * Version: 1.0
 */

public class Test6 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService pool = Executors.newFixedThreadPool(10);

        CompletableFuture<String> futureA = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextLong(5000));
                    System.out.println(Thread.currentThread().getName() + ">>>>futureA done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "A";
            }
        }, pool);

        CompletableFuture<String> futureB = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextLong(5000));
                    System.out.println(Thread.currentThread().getName() + ">>>>futureB done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "B";
            }
        }, pool);

        CompletableFuture<String> futureC = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextLong(5000));
                    System.out.println(Thread.currentThread().getName() + ">>>>futureC done");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "C";
            }
        }, pool);


        //使用allOf
//        CompletableFuture<Void> future = CompletableFuture.allOf(futureA, futureB, futureC);
//        future.handleAsync(new BiFunction<Void, Throwable, Object>() {
//            @Override
//            public Object apply(Void aVoid, Throwable throwable) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("all futures completed.");
//                return null;
//            }
//        }, pool);

        CompletableFuture<Object> future = CompletableFuture.anyOf(futureA, futureB, futureC);
        System.out.println("anyOf future ,result is:" + future.get());

        //并不会阻塞主线程
        System.out.println("----all futures completed.");

        pool.shutdown();
    }
}
