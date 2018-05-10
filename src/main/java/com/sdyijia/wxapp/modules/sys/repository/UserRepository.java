package com.sdyijia.wxapp.repository;

import com.sdyijia.wxapp.modules.sys.bean.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<SysUser, Long>, JpaSpecificationExecutor<SysUser> {
    /**
     * 通过username查找用户信息;
     */
    SysUser findByUsername(String username);


    Page<SysUser> findByNameLike(String name, Pageable pageable);

    List<SysUser> findByName(String name);

}
