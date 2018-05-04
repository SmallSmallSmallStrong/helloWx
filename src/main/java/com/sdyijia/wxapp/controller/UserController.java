package com.sdyijia.wxapp.controller;

import com.alibaba.fastjson.JSONArray;
import com.sdyijia.wxapp.bean.SysRole;
import com.sdyijia.wxapp.bean.SysUser;
import com.sdyijia.wxapp.repository.RoleRepository;
import com.sdyijia.wxapp.repository.UserRepository;
import com.sdyijia.wxapp.service.SysUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${reset_password}")
    private String restpwd;


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
    @GetMapping("/userAdd")
    @RequiresPermissions("userInfo:add")//权限管理;
    public String userInfoAdd() {
        //使用用户管理员（具有该权限的）直接添加用户
        return PREFIX + "useradd";
    }

    /**
     * 用户添加;
     *
     * @return
     */
    @PostMapping("/userAdd")
    @RequiresPermissions("userInfo:add")//权限管理;
    public String userInfoAdd(SysUser user, Model m) throws Exception {
        if (Objects.nonNull(user) && !"".equals(user.getUsername().trim()) && Objects.nonNull(user.getState()) && !"".equals(user.getPassword().trim()) && !"".equals(user.getName().trim())) {
            sysUserService.save(user);
        }
        return "redirect:userList";
    }

    /**
     * 更新用户信息 不包括账户名，密码
     *
     * @param id 更新那个user
     * @param m
     * @return
     */
    @GetMapping("/userUpdata")
    public String userUpdata(Long id, Model m) {
        SysUser sysUser = null;
        if (Objects.nonNull(id))
            sysUser = userRepository.getOne(id);
        m.addAttribute("user", sysUser);
        return "user/userupdata";
    }

    /**
     * 更新用户信息 不包括账户名，密码
     *
     * @param user 更新后的user
     * @return
     */
    @PostMapping("/userUpdata")
    public String userUpdata(SysUser user) throws Exception {
        if (Objects.nonNull(user) && Objects.nonNull(user.getId())) {
            SysUser dbuser = userRepository.getOne(user.getId());
            dbuser.setName(user.getName());
            dbuser.setState(user.getState());
            if (user.getSalt() != null && !user.getSalt().trim().equals("") && Objects.nonNull(user.getPassword()) && !user.getPassword().trim().equals("")) {
                dbuser.setSalt(user.getSalt());
                dbuser.setPassword(user.getPassword());
                sysUserService.save(dbuser);
            } else {
                userRepository.save(dbuser);
            }
        }
        return "redirect:userList";
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

    /**
     * 重置密码
     *
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/resetpwd")
    public String resetpwd(Long id) throws Exception {
        SysUser user = userRepository.getOne(id);
        user.setPassword(restpwd);
        sysUserService.save(user);
        return "redirect:userList";
    }

    /**
     * 查询所有角色跳转角色页面
     *
     * @param id
     * @param m
     * @return
     */
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

    /**
     * 设置角色
     *
     * @param id
     * @param rids
     * @return
     */
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
