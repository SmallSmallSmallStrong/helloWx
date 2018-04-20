package com.sdyijia.wxapp.bean;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 * 微信用户实体类
 */
@Entity
public class WxUser extends Base {

    private String openid;

    @OneToOne()
    private SysUser sysUser;

    private String sessionKey;


    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
}
