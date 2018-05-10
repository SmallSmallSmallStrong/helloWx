package com.sdyijia.wxapp.modules.sys.controller;

import com.sdyijia.wxapp.modules.sys.bean.SysPermission;
import com.sdyijia.wxapp.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    //前缀--文件夹名--如果有多层文件夹就写多层
    private final String PREFIX = "permission/";


    @Autowired
    PermissionRepository permissionRepository;

    @GetMapping
    public String getPermissionList(Model m, String ids) {
        m.addAttribute("rolelist", getPermissionList(ids));
        return PREFIX + "rolelist";
    }

    /**
     * 根据ids查询角色对应权限。如果ids为空则查询所有。
     *
     * @param ids 多个id使用空格分隔
     * @return
     */
    List<SysPermission> getPermissionList(String ids) {
        List<SysPermission> permissionlist = new ArrayList<>();
        if (ids != null && !ids.trim().equals("")) {
            String[] idsarr = ids.split(",");
            if (idsarr.length > 0) {
                List<Long> idslist = Arrays.stream(idsarr).map(Long::parseLong).collect(Collectors.toList());
                permissionlist = permissionRepository.findAllById(idslist);
                return permissionlist;
            }
        } else {
            permissionlist = permissionRepository.findAll();
            return permissionlist;
        }
        return Objects.requireNonNull(permissionlist);
    }





}
