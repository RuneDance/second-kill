package com.yyt.secondkill.redis;

public class SecondKillKey extends BasePrefix {

    private SecondKillKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static SecondKillKey isGoodsOver = new SecondKillKey(0, "go");
    public static SecondKillKey getSecondKillPath = new SecondKillKey(60, "mp");
    public static SecondKillKey getSecondKillVerifyCode = new SecondKillKey(300, "vc");
}