package com.sdyijia.wxapp.controller;

import com.sdyijia.wxapp.bean.SysUser;
import com.sdyijia.wxapp.repository.UserRepository;
import com.sdyijia.wxapp.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    SysUserService sysUserService;


    @GetMapping
    public String reg() {
        return "reg";
    }

    /**
     * 用户注册页面
     *
     * @param user
     * @return
     */
    @PostMapping("/")
    public String reg(@Valid SysUser user, Model m) throws Exception {
        //Set<Long> roleIds = Arrays.stream(roles).map(Long::valueOf).collect(Collectors.toSet());
        //Set<Role> roleSet = roleRepository.findAll(roleIds).stream().collect(Collectors.toSet());
        if (user != null && !user.getUsername().trim().equals("")) {
            user.setUsername(user.getUsername());
        } else if (user.getName() != null && !user.getName().equals("")) {
            user.setName(user.getName());
        } else if (user.getPassword() != null && !user.getPassword().equals("")) {
            user.setPassword(user.getPassword());
        } else {
            m.addAttribute("message", "用户信息填写不完整");
            return "error/error";
        }
        user.setState((byte) 0);
        sysUserService.save(user);
        return "redirect:/login";
    }


    /**
     * 用户查询.
     *
     * @return
     */
    @RequestMapping("/userList")
    @RequiresPermissions("userInfo:view")//权限管理;
    public String userInfo(Model m) {
        List<SysUser> all = userRepository.findAll();
        m.addAttribute("userlist", all);
        return "user/userlist";
    }

    /**
     * 用户添加;
     *
     * @return
     */
    @RequestMapping("/userAdd")
    @RequiresPermissions("userInfo:add")//权限管理;
    public String userInfoAdd() {
        //TODO 使用管理员用户直接添加用户
        return "userInfoAdd";
    }

    /**
     * 用户删除;
     *
     * @return
     */
    @RequestMapping("/del")
    @RequiresPermissions("userInfo:del")//权限管理;
    public String userDel(Long id) {
        userRepository.deleteById(id);
        return "redirect:userList";
    }

    @GetMapping("/resetpwd")
    public String resetpwd(Long id) {
        //TODO 密码重置
        return "redirect:userList";
    }
}
