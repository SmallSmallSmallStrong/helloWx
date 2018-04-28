package com.sdyijia.wxapp.controller;

import com.alibaba.fastjson.JSONArray;
import com.sdyijia.wxapp.bean.SysRole;
import com.sdyijia.wxapp.bean.SysUser;
import com.sdyijia.wxapp.repository.RoleRepository;
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
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    //前缀--文件夹名--如果有多层文件夹就写多层
    private final String PREFIX = "user/";


    @Autowired
    UserRepository userRepository;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    RoleRepository roleRepository;


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


    @GetMapping("role")
    public String getRole(Long id, Model m) {
        //获取所有角色
        if (Objects.nonNull(id) && id > 0) {
            List<SysRole> allrole = roleRepository.findAll();
            SysUser user = userRepository.getOne(id);
            //获取用户 的角色
            List<SysRole> userroles = user.getRoleList();
            JSONArray jsonarr = new JSONArray();
            if (Objects.nonNull(allrole))
                //过滤掉所有不能启用的role
                allrole.stream().filter(SysRole::getAvailable).forEach(role -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", role.getId());
                    map.put("name", role.getRole());
                    if (userroles.contains(role)) {
                        map.put("ischecked", 1);//具有该角色
                    } else {
                        map.put("ischecked", 0);//没有改角色
                    }
                    jsonarr.add(map);
                });
            m.addAttribute("json", jsonarr);
            m.addAttribute("id", id);
            return PREFIX + "userRole";
        }
        //请传值
        return "error";

    }

    @PostMapping("role")
    public String setRole(Long id, Long[] rids) {
        if (rids != null && rids.length > 0) {
            List<SysRole> roles = roleRepository.findAllById(Arrays.asList(rids));
            SysUser user = userRepository.getOne(id);
            user.setRoleList(roles);
            userRepository.save(user);
        }
        return "redirect:role?id=" + id;
    }


}
