package com.sdyijia.wxapp.modules.sys.controller.wx;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class WXApp {

    @RequestMapping("/api")
    public String api(HttpSession session, String data,String route) {
        System.out.println(data);
        Object json = JSONObject.parse(data);
        return "{a:123}";
    }
}
