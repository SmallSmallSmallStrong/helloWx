package com.sdyijia.wxapp.service;

import com.sdyijia.wxapp.bean.SysUser;
import com.sdyijia.wxapp.repository.UserRepository;
import com.sdyijia.wxapp.util.EncryptionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class SysUserService {

    @Resource
    private UserRepository userRepository;

    public SysUser findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userRepository.findByUsername(username);
    }

    /**
     * 该save方法会对pwd进行加密处理
     *
     * @return
     */
    public SysUser save(SysUser sysUser) {
        sysUser.setPassword(EncryptionUtils.getSha512Hash(sysUser.getPassword()));
        return userRepository.save(sysUser);
    }


}
