package com.sdyijia.wxapp.repository;

import com.sdyijia.wxapp.bean.SysRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<SysRole,Long> {

}
