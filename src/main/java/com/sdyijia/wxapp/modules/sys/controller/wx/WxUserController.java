package com.sdyijia.wxapp.modules.sys.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.sdyijia.wxapp.modules.sys.bean.ApiResult;
import com.sdyijia.wxapp.modules.sys.bean.SysUser;
import com.sdyijia.wxapp.modules.sys.bean.WxUser;
import com.sdyijia.wxapp.modules.sys.bean.WxUserInfo;
import com.sdyijia.wxapp.modules.sys.controller.BaseController;
import com.sdyijia.wxapp.modules.sys.repository.WxUserDao;
import com.sdyijia.wxapp.modules.sys.repository.WxUserInfoRepository;
import com.sdyijia.wxapp.properties.WxAppInfo;
import com.sdyijia.wxapp.modules.sys.service.SysUserService;
import com.sdyijia.wxapp.util.EncryptionUtils;
import com.sdyijia.wxapp.util.HttpUtil;
import com.sdyijia.wxapp.util.SzUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Objects;

@RequestMapping("wxuser")
@Controller
public class WxUserController extends BaseController {
    public final static Logger logger = LoggerFactory.getLogger(WxUserController.class);

    @Autowired
    WxUserDao wxUserDao;
    @Autowired
    WxUserInfoRepository wxUserInfoRepository;
    @Autowired
    WxAppInfo wxAppInfo;
    @Autowired
    SysUserService sysUserService;

    @PostMapping("/logincode")
    @ResponseBody
    public String logincode(@Valid String code) {
        //根据code访问微信服务器取得opid
        if (code != null && !code.equals("")) {
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wxAppInfo.getAppId().trim() + "&secret=" + wxAppInfo.getAppSecret().trim() + "&js_code=" + code.trim() + "&grant_type=authorization_code";
            JSONObject json = (JSONObject) HttpUtil.request(url);
            String openid = json.getString("openid");
            String sessionKey;
            sessionKey = json.getString("session_key");
            if (sessionKey != null && openid != null) {
                //使用 openid 创建； WxUser 用户
                WxUser wxUser;
                wxUser = wxUserDao.findByOpenid(openid);
                String sessionid = EncryptionUtils.getSha512Hash(openid);
                if (wxUser == null) {
                    wxUser = new WxUser();
                    wxUser.setOpenid(openid);
                    wxUser.setSessionKey(sessionKey);
                    wxUser.setSessionId(sessionid);
                    wxUserDao.save(wxUser);
                }
                String loginName = openid;
                logger.info("准备登陆微信用户 =>  " + loginName);
                UsernamePasswordToken token = new UsernamePasswordToken(openid, sessionid);
                //获取当前的Subject
                Subject currentUser = SecurityUtils.getSubject();
                ApiResult apiResult = null;
                try {
                    //在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
                    //所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
                    logger.info("对用户[" + loginName + "]进行登录验证..验证开始");
                    currentUser.login(token);
                    logger.info("对用户[" + loginName + "]进行登录验证..验证通过");
                } catch (UnknownAccountException uae) {
                    logger.info("对用户[" + loginName + "]进行登录验证..验证未通过,未知账户");
                    apiResult = new ApiResult(500, "{message:\"未知账户\"}");
                } catch (IncorrectCredentialsException ice) {
                    logger.info("对用户[" + loginName + "]进行登录验证..验证未通过,错误的凭证");
                    apiResult = new ApiResult(500, "{message:\"密码不正确\"}");
                } catch (LockedAccountException lae) {
                    logger.info("对用户[" + loginName + "]进行登录验证..验证未通过,账户已锁定");
                    apiResult = new ApiResult(500, "{message:\"账户已锁定\"}");
                } catch (AuthenticationException ae) {
                    //通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
                    logger.info("对用户[" + loginName + "]进行登录验证..验证未通过,堆栈轨迹如下");
                    ae.printStackTrace();
                    apiResult = new ApiResult(500, "{message:\"用户名或密码不正确\"}");
                }
                //验证是否登录成功
                if (currentUser.isAuthenticated()) {
                    logger.info("用户[" + loginName + "]登录认证通过(这里可以进行一些认证通过后的一些系统参数初始化操作)");
                    apiResult = new ApiResult(200, "{success:\"登录成功\"，data:{}}");
                    return apiResult.toJSONString();
                } else {
                    token.clear();
                    return apiResult.toJSONString();
                }
            } else {
                System.err.println(json);
            }
        }
        return new ApiResult(500, "登录异常").toJSONString();
    }

    @PostMapping("/getuserinfo")
    @ResponseBody
    public String getUserInfo(@Validated WxUserInfo wxuserinfo) {
        if (Objects.isNull(wxuserinfo) || Objects.isNull(wxuserinfo.getNickName())) {
            return new ApiResult(400, "传值为空").toJSONString();
        }
        Subject subject = SecurityUtils.getSubject();
        String openid = subject.getPrincipal().toString();
        wxuserinfo.setOpenid(openid);
        WxUserInfo dbwxuserinfo = wxUserInfoRepository.findByOpenid(openid);
        if (Objects.isNull(dbwxuserinfo)) {
            wxUserInfoRepository.save(wxuserinfo);
        }
        WxUser dbwxuser = wxUserDao.findByOpenid(openid);
        dbwxuser.setWxUserInfo(dbwxuserinfo);
        wxUserDao.save(dbwxuser);

        return new ApiResult(200, "ok").toJSONString();
    }

    @RequestMapping("/reg")
    @ResponseBody
    public String reg(SysUser sysUser) {
        Subject subject = SecurityUtils.getSubject();
        ApiResult apiResult = null;
        if (subject.isAuthenticated()) {
            String openid = subject.getPrincipal().toString();
            WxUserInfo dbwxuserinfo = wxUserInfoRepository.findByOpenid(openid);
            if (SzUtil.nonNull(sysUser, SysUser.class, "name", "salt", "state", "roleList")) {
                //整上昵称
                sysUser.setName(dbwxuserinfo.getNickName());
                //看看数据库中是否存在该用户名的用户
                SysUser dbSysUser = sysUserService.findByUsername(sysUser.getUsername());
                if (dbSysUser == null) {
                    //表示第一次登陆啊
                    dbSysUser = sysUserService.save(sysUser);
                    WxUser dbWxUser = wxUserDao.findByOpenid(openid);
                    dbWxUser.setSysUser(dbSysUser);
                    wxUserDao.save(dbWxUser);
                    apiResult = new ApiResult(200, "注册成功");
                }else {
                    apiResult = new ApiResult(200, "该账号已被注册");
                }
                //接收微信端信息
                System.out.println(subject.getPrincipal().toString());

                return apiResult.toJSONString();
            } else {
                apiResult = new ApiResult(400, "注册传值为空");
                return apiResult.toJSONString();
            }
        } else {
//            apiResult = new ApiResult(500, "{data:'请重新登录'}");
            apiResult = new ApiResult(500, "请重新登录");
            return apiResult.toJSONString();
        }
    }

}
