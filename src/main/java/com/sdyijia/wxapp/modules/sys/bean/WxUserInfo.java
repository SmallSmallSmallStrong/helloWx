package com.sdyijia.wxapp.modules.sys.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.Max;

/**
 * 微信用户信息
 */
@Entity
public class WxUserInfo extends Base {
    @Column(unique = true)
    private String openid;
    private String nickName;
    //用户头像，最后一个数值代表正方形头像大小
    // （有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。
    // 若用户更换头像，原有头像URL将失效。
    @Lob
    private String avatarUrl;
    //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    @Max(value = 2)
    private Integer gender;
    private String city;
    private String province;
    private String country;
    private String language;

    @Override
    public String toString() {
        return "WxUserInfo{" +
                "id='" + getId() + '\'' +
                ", openid='" + openid + '\'' +
                ", nickName='" + nickName + '\'' +
                ", avatarUrl='" + avatarUrl + '\'' +
                ", gender=" + gender +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", language='" + language + '\'' +
                '}';
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
