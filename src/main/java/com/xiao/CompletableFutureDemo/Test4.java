package com.xiao.CompletableFutureDemo;

import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * Description: thenCombine 并发组合
 * User: xiaojixiang
 * Date: 2017/4/27
 * Version: 1.0
 */

public class Test4 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        //使用自定义线程池
        ExecutorService pool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() + 2, 1000 * 60 * 10, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100));

        CompletableFuture<Integer> futureA = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                System.out.println(Thread.currentThread().getName() + ">>>futureA start");
                try {
                    Thread.sleep(1000 * 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ">>>futureA end");
                return 10;
            }
        }, pool);

        CompletableFuture<String> futureB = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                System.out.println(Thread.currentThread().getName() + ">>>futureB start");
                return "20";
            }
        }, pool);

        CompletableFuture<Integer> futureC = futureA.thenCombineAsync(futureB, new BiFunction<Integer, String, Integer>() {
            @Override
            public Integer apply(Integer integer, String s) {
                System.out.println(Thread.currentThread().getName() + ">>>futureC start");
                return integer + Integer.valueOf(s);
            }
        },pool);    //切换了线程

        System.out.println(String.valueOf(futureC.get()));

        pool.shutdown();
    }
}
