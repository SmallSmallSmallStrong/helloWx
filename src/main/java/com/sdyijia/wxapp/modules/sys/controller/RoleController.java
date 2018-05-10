package com.sdyijia.wxapp.modules.sys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sdyijia.wxapp.modules.sys.bean.SysPermission;
import com.sdyijia.wxapp.modules.sys.bean.SysRole;
import com.sdyijia.wxapp.repository.PermissionRepository;
import com.sdyijia.wxapp.repository.RoleRepository;
import com.sdyijia.wxapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("role")
public class RoleController {

    //前缀--文件夹名--如果有多层文件夹就写多层
    private final String PREFIX = "role/";

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PermissionRepository permissionRepository;

    /**
     * 获取全部或者指定id的角色
     *
     * @param m
     * @param ids 如果是多个id请使用逗号分隔
     * @return
     */
    @GetMapping
    public String getRoleList(Model m, String ids) {
        m.addAttribute("rolelist", getRoleList(ids));
        return PREFIX + "rolelist";
    }


    /**
     * 通过角色id查询该角色的权限列表
     *
     * @param ids
     * @return
     */
    @GetMapping("/permission")
    public ModelAndView permission(String ids) {
        SysRole role = roleRepository.getOne(Long.parseLong(ids));
        List<SysPermission> rolePremissionList = role.getPermissions();
        ModelAndView mv = new ModelAndView(PREFIX + "rolepremission");
        List<SysPermission> perlist = permissionRepository.findAll();
        if (Objects.nonNull(perlist) && perlist.size() > 0) {
            rolePremissionList.sort(Comparator.comparing(p -> p.getId()));
            JSONArray json = new JSONArray();
            perlist.forEach(premission -> {
                Map<String, Object> map = new HashMap<>();
                map.put("name", premission.getName());
                map.put("parentid", premission.getParentId());
                map.put("id", premission.getId());
                map.put("route", premission.getUrl());
                boolean rol = rolePremissionList.contains(premission);
                if (rol) {
                    map.put("ischeck", 1);//证明在里面
                } else {
                    map.put("ischeck", 0);//不在里面
                }
                json.add(map);
            });
            mv.addObject("json", json);
        }
        mv.addObject("role", JSON.toJSONString(role));
        return mv;
    }

    @PostMapping("/permission")
    public String permission(Long[] permissionId, Long roleId) {
        if (Objects.nonNull(roleId)) {
            SysRole role = roleRepository.getOne(roleId);
            List<SysPermission> permissions = permissionRepository.findAllById(Arrays.asList(permissionId));
            System.out.println(Arrays.toString(permissionId));
            role.setPermissions(permissions);
            roleRepository.save(role);
        }
        return "redirect:permission?ids=" + roleId;
    }

    /**
     * 删除指定id的一个或多个角色
     *
     * @param ids 如果是多个id请使用逗号分隔
     * @return
     */
    @GetMapping("/delete")
    public String getRoleDelete(String ids) {
        if (ids == null || "".equals(ids.trim()))
            return "redirect:";
        String[] strids = ids.split(",");
        List<Long> list = null;
        if (strids != null && strids.length > 0)
            list = Arrays.stream(strids).map(Long::parseLong).collect(Collectors.toList());
        if (Objects.nonNull(list) && list.size() > 0) {
            list.forEach(roleid -> {
                roleRepository.deleteById(roleid);
            });
        }
        return "redirect:";
    }


    /**
     * 获取要编辑的id并且跳转到对应页面
     *
     * @param m
     * @param id
     * @return
     */
    @GetMapping("/toupdate")
    public String toupload(Model m, String id) {
        SysRole sysrole = getRoleList(id).get(0);
        m.addAttribute("role", sysrole);
        return PREFIX + "roleupdata";
    }

    @PostMapping("/updata")
    public String update(SysRole role, Model m) {
        String name = role.getRole();
        String des = role.getDescription();
        Boolean ava = role.getAvailable();
        if (Objects.isNull(name) || "".equals(name.trim()) || Objects.isNull(des) || "".equals(des.trim())) {
            m.addAttribute("message", "数据格式传输错误");
            return "error";
        }
        Long id = role.getId();
        if (Objects.nonNull(id)) {
            SysRole sysRole = roleRepository.getOne(id);
            sysRole.setRole(name);
            sysRole.setDescription(des);
            sysRole.setAvailable(ava);
            roleRepository.save(sysRole);
            return "redirect:";
        } else {
            m.addAttribute("message", "数据格式传输错误");
            return "error";
        }
    }

    /**
     * 跳转角色添加页面
     *
     * @return
     */
    @GetMapping("/toadd")
    public String toadd() {
        return PREFIX + "roleadd";
    }

    @PostMapping("/roleadd")
    public String roleadd(SysRole sysRole) {
        System.out.println(sysRole.getAvailable());
        roleRepository.save(sysRole);
        return "redirect:" + "";
    }


    /**
     * 根据ids查询如果ids为null则查询所有的
     *
     * @param ids
     * @return
     */
    private List<SysRole> getRoleList(String ids) {
        if (ids != null && !ids.trim().equals("")) {
            String[] idsarr = ids.split(",");
            if (idsarr.length > 0) {
                List<Long> idslist = Arrays.stream(idsarr).map(Long::parseLong).collect(Collectors.toList());
                List<SysRole> rolelist = roleRepository.findAllById(idslist);
                return rolelist;
            }
        } else {
            List<SysRole> rolelist = roleRepository.findAll();
            return rolelist;
        }
        return new ArrayList<>();
    }

}
