package com.yyt.secondkill.redis;

public interface KeyPrefix {
    //过期时间
    int expireSeconds();

    //后缀
    String getPrefix();

}