package com.xiao.lettuceDemo;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.api.sync.RedisCommands;
import com.lambdaworks.redis.codec.ByteArrayCodec;
import com.xiao.config.cache.util.SerializationUtil;

import java.io.Serializable;

/**
 * Description:
 * User: xiaojixiang
 * Date: 2017/5/2
 * Version: 1.0
 */

public class Demo2 implements Serializable {

    private String username;
    private int age;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Demo2{" +
                "username='" + username + '\'' +
                ", age=" + age +
                '}';
    }

    public static void main(String[] args) throws InterruptedException {
        RedisClient redisClient = RedisClient.create("redis://192.168.8.102");

        RedisCommands<byte[], byte[]> redisCommands = redisClient.connect(new ByteArrayCodec()).sync();
        redisCommands.set("hello1".getBytes(), "world".getBytes());
        System.out.println(redisCommands.get("hello".getBytes()));
        redisCommands.set("hello2".getBytes(), "world2".getBytes());

        Demo2 d = new Demo2();
        d.setUsername("xiaojixiang");
        d.setAge(33);
        redisCommands.set("hello3".getBytes(), SerializationUtil.serialize(d));

        byte[] bytes = redisCommands.get("hello3".getBytes());
        Demo2 u2 = (Demo2)SerializationUtil.deserialize(bytes);
        System.out.println(u2.toString());

        redisCommands.close();

        Thread.sleep(Integer.MAX_VALUE);
    }
}
