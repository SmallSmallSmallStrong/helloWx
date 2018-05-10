package com.sdyijia.wxapp.modules.sys.bean;

import javax.persistence.*;

/**
 * 微信用户实体类
 */
@Entity
public class WxUser extends Base {

    @Column(unique = true)
    private String openid;

    @OneToOne()
    private SysUser sysUser;

    private String sessionKey;
    /**
     * 可以跟微信客户端传输的seeionid
     */
    private String sessionId;
    /**
     * 加密密码的盐。
     * 默认使用util包EncryptionUtils类的一个常量来做盐，使用该常量时该属性仍为空。
     * 当不设置该属性时则：password = （（EncryptionUtils.salt + 明文password）的加密）
     */
    private String salt;

    @OneToOne
    private WxUserInfo wxUserInfo;

    @Override
    public String toString() {
        return "WxUser{" +
                "openid='" + openid + '\'' +
                ", sessionKey='" + sessionKey + '\'' +
                ", sessionId='" + sessionId + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }

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

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public WxUserInfo getWxUserInfo() {
        return wxUserInfo;
    }

    public void setWxUserInfo(WxUserInfo wxUserInfo) {
        this.wxUserInfo = wxUserInfo;
    }
}
