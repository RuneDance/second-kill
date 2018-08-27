package com.yyt.secondkill.service;


import com.yyt.secondkill.dao.OrderDao;
import com.yyt.secondkill.entity.Order;
import com.yyt.secondkill.entity.SecondKillOrder;
import com.yyt.secondkill.entity.SecondKillUser;
import com.yyt.secondkill.redis.OrderKey;
import com.yyt.secondkill.redis.RedisService;
import com.yyt.secondkill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private RedisService redisService;

    /**
     * 通过userId,goodsId获取秒杀订单
     *
     * @param userId
     * @param goodsId
     * @return
     */
    public SecondKillOrder getSecondKillOrderByUserIdGoodsId(long userId, long goodsId) {
        return redisService.get(OrderKey.getSecondKillOrderByUidGid, "" + userId + "_" + goodsId, SecondKillOrder.class);
    }

    public Order getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }


    @Transactional
    public Order createOrder(SecondKillUser user, GoodsVo goods) {
        Order order = new Order();
        order.setCreateDate(new Date());
        order.setReceiveAddressId(0L);
        order.setGoodsCount(1);
        order.setGoodsId(goods.getId());
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsPrice(goods.getSecondKillPrice());
        order.setOrderChannel(1);
        order.setOrderStatus(0);
        order.setUserId(user.getId());
        orderDao.insert(order);
        SecondKillOrder secondKillOrder = new SecondKillOrder();
        secondKillOrder.setGoodsId(goods.getId());
        secondKillOrder.setOrderId(order.getId());
        secondKillOrder.setUserId(user.getId());
        orderDao.insertSecondKillOrder(secondKillOrder);

        redisService.set(OrderKey.getSecondKillOrderByUidGid, "" + user.getId() + "_" + goods.getId(), secondKillOrder);

        return order;
    }

    public void deleteOrders() {
        orderDao.deleteOrders();
        orderDao.deleteSecondKillOrders();
    }

}
