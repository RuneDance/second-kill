package com.yyt.secondkill.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 秒杀商品信息
 */
public class SecondKillGoods {

    //秒杀的商品表ID
    private Long id;

    //商品ID
    private Long goodsId;

    //秒杀价
    private BigDecimal secondKillPrice;

    //库存数量
    private Integer stockCount;

    //秒杀开始时间
    private Date startDate;

    //秒杀结束时间
    private Date endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public BigDecimal getSecondKillPrice() {
        return secondKillPrice;
    }

    public void setSecondKillPrice(BigDecimal secondKillPrice) {
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
}
