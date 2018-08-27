package com.yyt.secondkill.controller;

import com.yyt.secondkill.access.AccessLimit;
import com.yyt.secondkill.entity.SecondKillOrder;
import com.yyt.secondkill.entity.SecondKillUser;
import com.yyt.secondkill.rabbitmq.MQSender;
import com.yyt.secondkill.rabbitmq.SecondKillMessage;
import com.yyt.secondkill.redis.GoodsKey;
import com.yyt.secondkill.redis.OrderKey;
import com.yyt.secondkill.redis.RedisService;
import com.yyt.secondkill.redis.SecondKillKey;
import com.yyt.secondkill.result.CodeMsg;
import com.yyt.secondkill.result.Result;
import com.yyt.secondkill.service.GoodsService;
import com.yyt.secondkill.service.OrderService;
import com.yyt.secondkill.service.SecondKillService;
import com.yyt.secondkill.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/second_kill")
public class SecondKillController implements InitializingBean {

    @Autowired
    private RedisService redisService;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private SecondKillService secondKillService;

    @Autowired
    private MQSender mqSender;

    private HashMap<Long, Boolean> hashMap = new HashMap<Long, Boolean>();

    /**
     * 系统初始化
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        if (goodsList.size() == 0) {
            return;
        }
        for (GoodsVo goods : goodsList) {
            redisService.set(GoodsKey.getSecondKillGoodsStock, "" + goods.getId(), goods.getStockCount());
            hashMap.put(goods.getId(), false);
        }
    }

    /**
     * 重置
     *
     * @return
     */
    @RequestMapping(value = "/reset", method = RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> reset() {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        for (GoodsVo goods : goodsList) {
            goods.setStockCount(10);
            redisService.set(GoodsKey.getSecondKillGoodsStock, "" + goods.getId(), 10);
            hashMap.put(goods.getId(), false);
        }
        redisService.delete(OrderKey.getSecondKillOrderByUidGid);
        redisService.delete(SecondKillKey.isGoodsOver);
        secondKillService.reset(goodsList);
        return Result.success(true);
    }

    /**
     * 秒杀
     *
     * @param model
     * @param user
     * @param goodsId
     * @param path
     * @return
     */
    @RequestMapping(value = "/{path}/do_second_kill", method = RequestMethod.POST)
    @ResponseBody
    public Result<Integer> secondKill(Model model, SecondKillUser user, @RequestParam("goodsId") long goodsId, @PathVariable("path") String path) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //验证path
        boolean check = secondKillService.checkPath(user, goodsId, path);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        //内存标记，减少redis访问
        boolean over = hashMap.get(goodsId);
        if (over) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //预减库存
        long stock = redisService.decr(GoodsKey.getSecondKillGoodsStock, "" + goodsId);//10
        if (stock < 0) {
            hashMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        //判断是否已经秒杀到了
        SecondKillOrder order = orderService.getSecondKillOrderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //入队
        SecondKillMessage secondKillMessage = new SecondKillMessage();
        secondKillMessage.setSecondKillUser(user);
        secondKillMessage.setGoodsId(goodsId);
        mqSender.sendSecondKillMessage(secondKillMessage);
        return Result.success(0);//排队中
    }

    /**
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     */
    @RequestMapping(value = "/result", method = RequestMethod.GET)
    @ResponseBody
    public Result<Long> secondKillResult(Model model, SecondKillUser user, @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = secondKillService.getSecondKillResult(user.getId(), goodsId);
        return Result.success(result);
    }

    @AccessLimit(seconds = 5, maxCount = 5, needLogin = true)
    @RequestMapping(value = "/path", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getSecondKillPath(SecondKillUser user, @RequestParam("goodsId") long goodsId, @RequestParam(value = "verifyCode", defaultValue = "0") int verifyCode) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        boolean check = secondKillService.checkVerifyCode(user, goodsId, verifyCode);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        String path = secondKillService.createSecondKillPath(user, goodsId);
        return Result.success(path);
    }

    /**
     * 验证码校验
     *
     * @param response
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getSecondKillVerifyCod(HttpServletResponse response, SecondKillUser user, @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        try {
            BufferedImage image = secondKillService.createVerifyCode(user, goodsId);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.MIAOSHA_FAIL);
        }
    }


}
