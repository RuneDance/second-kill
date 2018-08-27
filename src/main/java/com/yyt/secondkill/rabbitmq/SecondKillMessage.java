package com.yyt.secondkill.rabbitmq;


import com.yyt.secondkill.entity.SecondKillUser;

public class SecondKillMessage {

    private long goodsId;

    private SecondKillUser secondKillUser;

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public SecondKillUser getSecondKillUser() {
        return secondKillUser;
    }

    public void setSecondKillUser(SecondKillUser secondKillUser) {
        this.secondKillUser = secondKillUser;
    }

}
