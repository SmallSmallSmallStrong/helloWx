package com.sdyijia.wxapp.modules.sys.repository;

import com.sdyijia.wxapp.modules.sys.bean.WxUserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WxUserInfoRepository extends JpaRepository<WxUserInfo, Long>, JpaSpecificationExecutor<WxUserInfo> {
    WxUserInfo findByOpenid(String openid);
}
