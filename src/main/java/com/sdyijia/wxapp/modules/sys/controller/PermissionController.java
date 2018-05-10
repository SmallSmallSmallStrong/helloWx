package com.sdyijia.wxapp.modules.sys.controller;

import com.sdyijia.wxapp.modules.sys.bean.SysPermission;
import com.sdyijia.wxapp.modules.sys.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/permission")
public class PermissionController {

    //前缀--文件夹名--如果有多层文件夹就写多层
    private final String PREFIX = "permission/";


    @Autowired
    PermissionRepository permissionRepository;

    @GetMapping
    public String getPermissionList(Model m) {
        List<SysPermission> allByParentId = permissionRepository.findAllByParentIdOrderByIdAsc(0l);
        m.addAttribute("permlist", allByParentId);
        return PREFIX + "permlist";
    }

    /**
     * 删除权限分组
     *
     * @param m
     * @param ids
     * @return
     */
    @GetMapping("/delete")
    @Transactional
    public String delete(Model m, String ids) {
        long l = Long.parseLong(ids);
        permissionRepository.deleteAllByParentId(l);
        permissionRepository.deleteById(l);
        return "redirect:";
    }

    /**
     * 权限列表
     *
     * @param m
     * @param ids
     * @return
     */
    @GetMapping("/functionlist")
    public String functionList(Model m, String ids) {
        long l = Long.parseLong(ids);
        List<SysPermission> allByParentId = permissionRepository.findAllByParentIdOrderByIdAsc(l);
        m.addAttribute("functionlist", allByParentId);
        m.addAttribute("parentId", l);
        return PREFIX + "functionlist";
    }

    /**
     * 保存或修改权限分组
     *
     * @param m
     * @return
     */
    @PostMapping("/modify")
    public String modifyPermission(Model m, SysPermission perm) {
        if (perm.getId() == null) {
            permissionRepository.save(perm);
        }else {
            Optional<SysPermission> byId = permissionRepository.findById(perm.getId());
            if (byId.isPresent()) {
                SysPermission sysPermission = byId.get();
                sysPermission = copyPerm(sysPermission, perm);
                permissionRepository.save(sysPermission);
            }
        }
        return "redirect:";
    }

    private SysPermission copyPerm(SysPermission oldperm, SysPermission newperm) {
        if (newperm.getAvailable() != null) {
            oldperm.setAvailable(newperm.getAvailable());
        }
        if (newperm.getName() != null && newperm.getName().trim().length() != 0) {
            oldperm.setName(newperm.getName());
        }
        if (newperm.getResourceType() != null && newperm.getResourceType().trim().length() != 0) {
            oldperm.setResourceType(newperm.getResourceType());
        }
        if (newperm.getUrl() != null && newperm.getUrl().trim().length() != 0) {
            oldperm.setUrl(newperm.getUrl());
        }
        if (newperm.getPermission() != null && newperm.getPermission().trim().length() != 0) {
            oldperm.setPermission(newperm.getPermission());
        }
        if (newperm.getParentId() != null ) {
            oldperm.setParentId(newperm.getParentId());
        }
        return oldperm;
    }

    /**
     * 跳转到增加修改权限分组界面
     *
     * @param m
     * @param ids
     * @return
     */
    @GetMapping("/toadd")
    public String modifyPermission(Model m, String ids, String parentId) {
        if (ids != null && ids != "" && ids.trim().length() > 0) {
            long l = Long.parseLong(ids);
            Optional<SysPermission> byId = permissionRepository.findById(l);
            if (byId.isPresent()) {
                SysPermission sysPermission = byId.get();
                m.addAttribute("perm", sysPermission);
            }
        } else if (parentId != null && ids != "" && parentId.trim().length() > 0) {
            long l = Long.parseLong(parentId);
            Optional<SysPermission> byId = permissionRepository.findById(l);
            if (byId.isPresent()) {
                SysPermission sysPermission = byId.get();
                m.addAttribute("parentId", sysPermission.getId());
            }
        }
        return PREFIX + "permadd";
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
