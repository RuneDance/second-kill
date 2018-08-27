package com.yyt.secondkill.rabbitmq;


import com.yyt.secondkill.entity.SecondKillOrder;
import com.yyt.secondkill.entity.SecondKillUser;
import com.yyt.secondkill.redis.RedisService;
import com.yyt.secondkill.service.GoodsService;
import com.yyt.secondkill.service.OrderService;
import com.yyt.secondkill.service.SecondKillService;
import com.yyt.secondkill.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {
    private static Logger log = LoggerFactory.getLogger(MQReceiver.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SecondKillService secondKillService;

    @RabbitListener(queues = MQConfig.SECOND_KILL_QUEUE)
    public void receive(String message) {
        log.info("receive message:" + message);
        SecondKillMessage mm = redisService.stringToBean(message, SecondKillMessage.class);
        SecondKillUser user = mm.getSecondKillUser();
        long goodsId = mm.getGoodsId();

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return;
        }
        //判断是否已经秒杀到了
        SecondKillOrder order = orderService.getSecondKillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return;
        }
        //减库存 下订单 写入秒杀订单
        secondKillService.secondKill(user, goods);
    }

}
