package com.yyt.secondkill.redis;

public class GoodsKey extends BasePrefix {

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(60, "gl");
    //商品
    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");
    //商品库存Key前缀
    public static GoodsKey getSecondKillGoodsStock = new GoodsKey(0, "gs");
}