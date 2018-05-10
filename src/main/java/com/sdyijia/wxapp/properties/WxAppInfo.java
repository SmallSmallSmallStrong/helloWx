package com.sdyijia.wxapp.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WxAppInfo {
    @Value("${wx.appid}")
    private String appId;
    @Value("${wx.appsecret}")
    private String appSecret;

    @Override
    public String toString() {
        return "WxAppInfo{" +
                "appId='" + appId + '\'' +
                ", appSecret='" + appSecret + '\'' +
                '}';
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}
