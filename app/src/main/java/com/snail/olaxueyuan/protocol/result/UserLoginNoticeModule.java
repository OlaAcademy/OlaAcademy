package com.snail.olaxueyuan.protocol.result;

import java.io.Serializable;

/**
 * Created by mingge on 2016/6/15.
 */
public class UserLoginNoticeModule implements Serializable {
    /**
     * true:登录成功,false:退出账号成功
     */
    public boolean isLogin = false;

    public UserLoginNoticeModule(boolean isLogin) {
        this.isLogin = isLogin;
    }
}
