package com.sdyijia.wxapp.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @RequestMapping("/login")
    public String login(HttpSession session,  String username, String pwd) {
        session.setAttribute("username",username);
        System.out.println(username);
        System.out.println(pwd);
        if (username == "admin") {
            Map<String, String> map = new HashMap<>();
            map.put("a", "1");
            map.put("b", "2");
            return success(map);
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("a", "1");
            map.put("b", "2");
            return fail(map);
        }
    }
}
