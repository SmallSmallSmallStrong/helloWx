package com.sdyijia.wxapp.modules.sys.repository;

import com.sdyijia.wxapp.modules.sys.bean.WxUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WxUserDao extends CrudRepository<WxUser, Long> {
    public WxUser findBySessionId(String sessionId);
    public WxUser findByOpenid(String openid);
}
