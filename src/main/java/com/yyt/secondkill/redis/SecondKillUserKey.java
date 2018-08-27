package com.yyt.secondkill.redis;

public class SecondKillUserKey extends BasePrefix {

    public static final int TOKEN_EXPIRE = 3600 * 24 * 2;

    private SecondKillUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static SecondKillUserKey token = new SecondKillUserKey(TOKEN_EXPIRE, "tk");
    public static SecondKillUserKey getById = new SecondKillUserKey(0, "id");
}