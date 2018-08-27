package com.yyt.secondkill.controller;


import com.yyt.secondkill.entity.Order;
import com.yyt.secondkill.entity.SecondKillUser;
import com.yyt.secondkill.redis.RedisService;
import com.yyt.secondkill.result.CodeMsg;
import com.yyt.secondkill.result.Result;
import com.yyt.secondkill.service.GoodsService;
import com.yyt.secondkill.service.OrderService;
import com.yyt.secondkill.service.SecondKillUserService;
import com.yyt.secondkill.vo.GoodsVo;
import com.yyt.secondkill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private SecondKillUserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(SecondKillUser user, @RequestParam("orderId") long orderId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        Order order = orderService.getOrderById(orderId);
        if (order == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);
        return Result.success(vo);
    }

}
