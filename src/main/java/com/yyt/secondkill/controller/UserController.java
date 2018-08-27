package com.yyt.secondkill.controller;

import com.yyt.secondkill.result.Result;
import com.yyt.secondkill.service.SecondKillUserService;
import com.yyt.secondkill.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private SecondKillUserService secondKillUserService;

    @RequestMapping("/login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, @Valid UserVo userVo) {
        log.info(userVo.toString());
        //登录
        String token = secondKillUserService.login(response, userVo);
        return Result.success(token);
    }

}
