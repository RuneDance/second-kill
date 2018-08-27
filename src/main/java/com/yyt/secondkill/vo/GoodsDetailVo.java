package com.yyt.secondkill.vo;

import com.yyt.secondkill.entity.SecondKillUser;

public class GoodsDetailVo {

    //秒杀状态
    private int secondKillStatus = 0;

    //剩余时间
    private int remainSeconds = 0;

    private GoodsVo goodsVo;

    private SecondKillUser secondKillUser;

    public int getSecondKillStatus() {
        return secondKillStatus;
    }

    public void setSecondKillStatus(int secondKillStatus) {
        this.secondKillStatus = secondKillStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }

    public SecondKillUser getSecondKillUser() {
        return secondKillUser;
    }

    public void setSecondKillUser(SecondKillUser secondKillUser) {
        this.secondKillUser = secondKillUser;
    }
}
