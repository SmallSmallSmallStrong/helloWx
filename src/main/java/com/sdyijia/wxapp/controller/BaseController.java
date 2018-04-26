package com.sdyijia.wxapp.controller;

import com.alibaba.fastjson.JSONObject;
import com.sdyijia.wxapp.bean.ApiResult;
import org.springframework.ui.Model;


public abstract class BaseController {


    public String success(Object object) {
        ApiResult res = new ApiResult(1, object);
        return JSONObject.toJSONString(res);
    }

    public String fail(Object object) {
        ApiResult res = new ApiResult(0, object);
        return JSONObject.toJSONString(res);
    }

}
