package com.xiao.CompletableFutureDemo;

/**
 * Description: User class
 * User: xiaojixiang
 * Date: 2017/4/27
 * Version: 1.0
 */

public class User {

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                '}';
    }
}
