package com.sdyijia.wxapp.repository;

import com.sdyijia.wxapp.modules.sys.bean.SysPermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<SysPermission, Long> {


}
