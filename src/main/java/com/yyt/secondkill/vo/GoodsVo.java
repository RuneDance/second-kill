package com.yyt.secondkill.vo;


import com.yyt.secondkill.entity.Goods;

import java.util.Date;

public class GoodsVo extends Goods {
    private Double secondKillPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
    private String goodsImg;

    public Double getSecondKillPrice() {
        return secondKillPrice;
    }

    public void setSecondKillPrice(Double secondKillPrice) {
        this.secondKillPrice = secondKillPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String getGoodsImg() {
        return goodsImg;
    }

    @Override
    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }
}
