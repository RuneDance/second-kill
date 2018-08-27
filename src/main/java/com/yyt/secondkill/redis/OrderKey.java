package com.yyt.secondkill.redis;

public class OrderKey extends BasePrefix {

    public OrderKey(String prefix) {
        super(prefix);
    }

    public static OrderKey getSecondKillOrderByUidGid = new OrderKey("moug");
}