package com.yyt.secondkill.service;


import com.yyt.secondkill.dao.SecondKillUserDao;
import com.yyt.secondkill.entity.SecondKillUser;
import com.yyt.secondkill.exception.GlobalException;
import com.yyt.secondkill.redis.RedisService;
import com.yyt.secondkill.redis.SecondKillUserKey;
import com.yyt.secondkill.result.CodeMsg;
import com.yyt.secondkill.util.MD5Util;
import com.yyt.secondkill.util.UUIDUtil;
import com.yyt.secondkill.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class SecondKillUserService {

    public static final String COOK_NAME_TOKEN = "token";

    @Autowired
    private SecondKillUserDao secondKillUserDao;

    @Autowired
    private RedisService redisService;

    /**
     * 根据Id获取redis中的缓存
     *
     * @param id
     * @return
     */
    public SecondKillUser getById(long id) {
        //取缓存
        SecondKillUser user = redisService.get(SecondKillUserKey.getById, "" + id, SecondKillUser.class);
        if (user != null) {
            return user;
        }
        //取数据库
        user = secondKillUserDao.getById(id);
        if (user != null) {
            redisService.set(SecondKillUserKey.getById, "" + id, user);
        }
        return user;
    }

    public boolean updatePassword(String token, long id, String password) {
        //取user
        SecondKillUser user = getById(id);
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //更新数据库
        SecondKillUser toBeUpdate = new SecondKillUser();
        toBeUpdate.setId(id);
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(password, user.getSalt()));
        secondKillUserDao.update(toBeUpdate);
        //处理缓存
        redisService.delete(SecondKillUserKey.getById, "" + id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(SecondKillUserKey.token, token, user);
        return true;
    }


    public SecondKillUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        SecondKillUser user = redisService.get(SecondKillUserKey.token, token, SecondKillUser.class);
        //延长有效期
        if (user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    /**
     * 用户登录
     *
     * @param response
     * @param userVo
     * @return
     */
    public String login(HttpServletResponse response, UserVo userVo) {
        if (userVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }
        String mobile = userVo.getMobile();
        String password = userVo.getPassword();
        //判断手机号是否存在
        SecondKillUser user = getById(Long.parseLong(mobile));
        if (user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(password, saltDB);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return token;
    }

    /**
     * 添加Cookie
     *
     * @param response
     * @param token
     * @param user
     */
    private void addCookie(HttpServletResponse response, String token, SecondKillUser user) {
        redisService.set(SecondKillUserKey.token, token, user);
        Cookie cookie = new Cookie(COOK_NAME_TOKEN, token);
        cookie.setMaxAge(SecondKillUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
