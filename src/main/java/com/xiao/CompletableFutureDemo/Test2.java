package com.xiao.CompletableFutureDemo;

import java.util.concurrent.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Description:
 * User: xiaojixiang
 * Date: 2017/4/26
 * Version: 1.0
 */

public class Test2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {

        ExecutorService executor = Executors.newFixedThreadPool(10);
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {
            return "zero";
        }, executor);

        //异步回调用  与 thenRun(Runnable action) 方法相同（接受一个runable）
        f1.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(Thread.currentThread().getName()+">>> f1.thenAccept1>>>>" + s);
            }
        });

        f1.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String s) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+">>>f1.thenAccept2>>>>" + s);
            }
        });

        //应用到f1的结果当作输入参数
        CompletableFuture<Integer> f2 = f1.thenApply(new Function<String, Integer>() {

            @Override
            public Integer apply(String t) {
                System.out.println(Thread.currentThread().getName()+">>>f2.thenApply>>>" + t); // "zero"
                return Integer.valueOf(t.length());
            }
        });

        CompletableFuture<Double> f3 = f2.thenApply(r -> r * 2.0);
        //System.out.println(f3.get());
        System.out.println("---------------------");


        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                try {
                    System.out.println(Thread.currentThread().getName()+">>>future2 get begin...");
                    Thread.sleep(1000 * 5);
                    System.out.println("future2 get end...");
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName()+">>>future2 InterruptedException >>>>>>>>>>>>" + e.getMessage());
                }

                return "hello";
            }
        }, executor);

        //异常处理
        future2.exceptionally(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable throwable) {
                System.out.println("future2 error:" + throwable.getMessage());
                return null;
            }
        });

        //completeExceptionally 置于 get方法之前，否则 future2 完成后，异常将不起作用
        //future2.completeExceptionally(new Exception("future2错误"));

        //推荐使用handle处理返回值和异常(在其他线程中执行，不会阻塞)
        future2.handle(new BiFunction<String, Throwable, Object>() {
            @Override
            public Object apply(String s, Throwable throwable) {
                if (s != null) {
                    System.out.println(Thread.currentThread().getName()+">>>>future2.handle, value:" + s);
                    return s;
                } else {
                    System.out.println(Thread.currentThread().getName()+">>>>future2.handle error:"+throwable.getMessage());
                    return "ERROR";
                }
            }
        });


        //future2.getNow("123");
        //future2.completeExceptionally(new Exception("future2错误"));
        //最多等待3S
        Thread.sleep(1000 * 3);

        //在CompletableFuture中，cancel并不能取消已在执行中的任务
        //see http://stackoverflow.com/questions/23320407/how-to-cancel-java-8-completable-future
        //boolean cancel = future2.cancel(true);
        //System.out.println("future2 canceled..." + cancel);

        System.out.println("Main complete...");
        executor.shutdown();
    }
}
