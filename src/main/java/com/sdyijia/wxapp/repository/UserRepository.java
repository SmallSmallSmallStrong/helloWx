package com.sdyijia.wxapp.repository;

import com.sdyijia.wxapp.bean.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<SysUser, Long> ,JpaSpecificationExecutor<SysUser> {
    /**
     * 通过username查找用户信息;
     */
    SysUser findByUsername(String username);

}
