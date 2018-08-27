package com.yyt.secondkill.vo;

import com.yyt.secondkill.validator.IsMobile;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class UserVo {

    //手机号
    @NotNull
    @IsMobile
    private String mobile;

    //密码
    @NotNull
    @Length(min = 32)
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserVo [mobile=" + mobile + ", password=" + password + "]";
    }
}
