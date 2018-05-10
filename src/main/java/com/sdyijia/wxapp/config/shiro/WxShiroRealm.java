package com.sdyijia.wxapp.config.shiro;

import com.sdyijia.wxapp.modules.sys.bean.SysPermission;
import com.sdyijia.wxapp.modules.sys.bean.SysRole;
import com.sdyijia.wxapp.modules.sys.bean.SysUser;
import com.sdyijia.wxapp.modules.sys.bean.WxUser;
import com.sdyijia.wxapp.repository.WxUserDao;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.logging.Logger;

public class WxShiroRealm extends AuthorizingRealm {
    private static final Logger logger = Logger.getLogger("MyShiroRealm");

    @Autowired
    private WxUserDao wxUserDao;

    /**
     * 对于微信用户权限的授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //通过PrincipalCollection获取当前用户信息
        SysUser userInfo = (SysUser) principals.getPrimaryPrincipal();
        if (Objects.nonNull(userInfo)) {
            for (SysRole role : userInfo.getRoleList()) {
                //在此处为用户设置角色
                authorizationInfo.addRole(role.getRole());
                for (SysPermission p : role.getPermissions()) {
                    //为用户设置权限
                    authorizationInfo.addStringPermission(p.getPermission());
                }
            }
        } else throw new AuthorizationException();
        logger.info("###【获取角色成功】[SessionId] =>  " + SecurityUtils.getSubject().getSession().getId());
        return authorizationInfo;
    }

    /**
     * 主要是用来进行微信登录身份认证的
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        logger.info("MyShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号.
        String openid = (String) token.getPrincipal();
        logger.info("token.getCredentials() ---- " + token.getCredentials());
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        WxUser userInfo = wxUserDao.findByOpenid(openid);
        if (userInfo == null) {
            return null;
        }
        logger.info("----->>userInfo=" + userInfo);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo.getOpenid(), //用户
                userInfo.getSessionId(), //密码
//                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );

        return authenticationInfo;
    }
}