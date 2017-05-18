package com.xiao.CompletableFutureDemo;

import java.util.concurrent.*;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Description: either 任意一个完成
 * User: xiaojixiang
 * Date: 2017/4/27
 * Version: 1.0
 */

public class Test5 {

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
                    Thread.sleep(ThreadLocalRandom.current().nextLong(50, 500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ">>>futureA end");
                return 10;
            }
        }, pool);

        CompletableFuture<Integer> futureB = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                System.out.println(Thread.currentThread().getName() + ">>>futureB start");
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextLong(50, 500));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return 20;
            }
        }, pool);

        //任意一个完成完成
        //注意线程切换的开销，考虑实际场景中带来的问题和效率的问题
        //比如以下程序futureC使用了futureA的运行线程来执行apply,   如果当futureA比futureB先完成，那么如果业务逻辑允许切换线程的话，
        //考虑使用使用applyToEitherAsync 切换线程执行applu，而futureC不需要等待futureA的线程
        CompletableFuture<Object> futureC = futureA.applyToEither(futureB, new Function<Integer, Object>() {
            @Override
            public Object apply(Integer integer) {
                return integer;
            }
        });

//        CompletableFuture<Object> futureC = futureA.applyToEitherAsync(futureB, new Function<Integer, Object>() {
//            @Override
//            public Object apply(Integer integer) {
//                return integer;
//            }
//        }, pool);

        System.out.println(String.valueOf(futureC.get()));
        pool.shutdown();
    }
}
