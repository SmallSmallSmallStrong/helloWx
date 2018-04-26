package com.sdyijia.wxapp.controller;

import com.sdyijia.wxapp.bean.SysPermission;
import com.sdyijia.wxapp.bean.SysRole;
import com.sdyijia.wxapp.repository.RoleRepository;
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
    @GetMapping("/premission")
    public ModelAndView permission(String ids, Model m) {
//        String[] strids = ids.split(",");
//        List<Long> list = null;
//        if (strids != null && strids.length > 0)
//            list = Arrays.stream(strids).map(Long::parseLong).collect(Collectors.toList());
//        if (Objects.nonNull(list) && list.size() == 1) {
//            list.forEach();
//        }
        SysRole role = roleRepository.getOne(Long.parseLong(ids));
        List<SysPermission> rolePremissionList = role.getPermissions();

        List<String> level = new ArrayList<>();
        List<String> prant = new ArrayList<>();
        List<String> id = new ArrayList<>();
        List<String> name = new ArrayList<>();
        //TODO 未写完待续
        rolePremissionList.forEach(premission -> {

            premission.getName();

            premission.getParentId();

            premission.getId();

            premission.getResourceType();
        });
//        JSONObject.toJSONString()
        ModelAndView mv = new ModelAndView(PREFIX + "rolepremission");
        return mv;
    }


    /**
     * 删除指定id的一个或多个角色
     *
     * @param ids 如果是多个id请使用逗号分隔
     * @return
     */
    @GetMapping("/delete")
    public String getRoleDelete(String ids) {
        String[] strids = ids.split(",");
        List<Long> list = null;
        if (strids != null && strids.length > 0)
            list = Arrays.stream(strids).map(Long::parseLong).collect(Collectors.toList());
        if (Objects.nonNull(list) && list.size() > 0) {
            list.forEach(roleid -> {
                roleRepository.deleteById(roleid);
            });
        }
        return PREFIX + "rolelist";
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
