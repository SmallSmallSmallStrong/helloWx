package com.sdyijia.wxapp.config.shiro;

import com.sdyijia.wxapp.modules.sys.bean.SysPermission;
import com.sdyijia.wxapp.modules.sys.bean.SysRole;
import com.sdyijia.wxapp.modules.sys.bean.SysUser;
import com.sdyijia.wxapp.modules.sys.repository.UserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;
import java.util.logging.Logger;

public class MyShiroRealm extends AuthorizingRealm {
    private static final Logger logger = Logger.getLogger("MyShiroRealm");

    @Autowired
    private UserRepository userRepository;

    /**
     * 权限的授权
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
     * 主要是用来进行身份认证的，也就是说验证用户输入的账号和密码是否正确
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws UnknownAccountException, LockedAccountException, IncorrectCredentialsException {
        logger.info("MyShiroRealm.doGetAuthenticationInfo()");
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        //获取用户的输入的账号.
        String username = usernamePasswordToken.getUsername();
        usernamePasswordToken.isRememberMe();
        //通过username从数据库中查找 User对象，如果找到，没找到.
        //实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        SysUser userInfo = userRepository.findByUsername(username);
        if (userInfo == null) {//没有改用户
            throw new UnknownAccountException();
        }
        if (userInfo.getState() == 2) {//用户锁定了
            throw new LockedAccountException();
        }
        if (!userInfo.getPassword().equals(usernamePasswordToken.getPassword())) {//密码不对
            throw new IncorrectCredentialsException();
        }
        logger.info("----->>userInfo=" + userInfo);
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo, //用户信息
                userInfo.getPassword(), //密码
//                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

}