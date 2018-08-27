package com.yyt.secondkill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class InitController {
    /**
     * 用户登录页面
     *
     * @return
     */
    @RequestMapping("/")
    public ModelAndView loginHtml() {
        return new ModelAndView("/login");
    }

    /**
     * 商品详情页面
     *
     * @return
     */
    @RequestMapping("/detail")
    public ModelAndView detailHtml() {
        return new ModelAndView("/detail");
    }

    /**
     * 订单详情页面
     *
     * @return
     */
    @RequestMapping("/order")
    public ModelAndView orderHtml() {
        return new ModelAndView("/order");
    }
}
