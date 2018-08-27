package com.yyt.secondkill.access;


import com.yyt.secondkill.entity.SecondKillUser;

public class UserContext {

    private static ThreadLocal<SecondKillUser> userHolder = new ThreadLocal<SecondKillUser>();

    public static void setUser(SecondKillUser user) {
        userHolder.set(user);
    }

    public static SecondKillUser getUser() {
        return userHolder.get();
    }

}
