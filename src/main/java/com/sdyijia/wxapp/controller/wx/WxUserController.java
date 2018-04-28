package com.sdyijia.wxapp.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.sdyijia.wxapp.bean.WxUser;
import com.sdyijia.wxapp.controller.BaseController;
import com.sdyijia.wxapp.properties.WxAppInfo;
import com.sdyijia.wxapp.repository.WxUserDao;
import com.sdyijia.wxapp.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

@RequestMapping("wxuser")
@Controller
public class WxUserController extends BaseController {

    @Autowired
    WxUserDao wxUserDao;

    @Autowired
    WxAppInfo wxAppInfo;


    @PostMapping("/logincode")
    @ResponseBody
    public String logincode(@Valid String code) {
        //根据code访问微信服务器取得opid
        if (code != null && !code.equals("")) {
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wxAppInfo.getAppId() + "&secret=" + wxAppInfo.getAppSecret() + "&js_code=" + code + "&grant_type=authorization_code";
            JSONObject json = (JSONObject) HttpUtil.request(url);
            String openid = json.getString("openid");
            String sessionKey;
            sessionKey = json.getString("session_key");
            if (sessionKey != null && openid != null) {
                //使用 openid 创建；临时 WxUser 用户
                WxUser wxUser = new WxUser();
                wxUser.setOpenid(openid);
                wxUser.setSessionKey(sessionKey);
                wxUserDao.save(wxUser);
                return JSONObject.toJSONString(wxUser);
            }
        }
        //根据opid以及获取到的微信信息为用户自动创建一个账号

        //使创建的账号处于登录状态
        //返回
        return "";
    }



    @RequestMapping("/reg")
    public String reg() {
        return "";
    }

}
