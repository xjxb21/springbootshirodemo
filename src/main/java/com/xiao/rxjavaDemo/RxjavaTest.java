package com.xiao.rxjavaDemo;

import rx.Observable;
import rx.Subscriber;

/**
 * Description: 简单demo
 * User: xiaojixiang
 * Date: 2017/4/28
 * Version: 1.0
 */

public class RxjavaTest {

    public static void main(String[] args) {

        Observable<String> myObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {


            }
        });
        
    }
}
