package com.xiao.CompletableFutureDemo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Description: lettuce Demo
 * User: xiaojixiang
 * Date: 2017/4/26
 * Version: 1.0
 */

public class Test1 extends Thread{

    public CompletableFuture future;
    public String operation;

    public static void main(String[] args) {
        CompletableFuture<String> future = new CompletableFuture<>();

        Test1 t1 = new Test1();
        t1.future = future;
        t1.operation = "operA";

        Test1 t2 = new Test1();
        t2.future = future;
        t2.operation = "operB";

        //线程启动后阻塞，直到future.complete方法调用后
        t1.start();
        t2.start();

        future.complete("xiao");
        System.out.println("test1 end....");
    }

    @Override
    public void run() {
        try {
            doSomething(this.future, this.operation);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void doSomething(CompletableFuture future, String operation) throws ExecutionException, InterruptedException {
        System.out.println(operation + ":" + future.get());
    }

}
