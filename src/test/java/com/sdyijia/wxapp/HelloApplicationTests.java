package com.sdyijia.wxapp;

import com.sdyijia.wxapp.modules.sys.bean.SysUser;
import com.sdyijia.wxapp.modules.sys.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloApplicationTests {


    @Autowired
    private UserRepository userRepository;


    @Test
    public void contextLoads() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<SysUser> alllist = userRepository.findAll(pageable);
        alllist.forEach(
                user -> {
                    System.out.println(user.getId());
                }
        );
        Page<SysUser> list = userRepository.findByNameLike("%e%", pageable);
        for (SysUser sysUser : list) {
            System.out.println(sysUser.getName());
        }
    }

}
