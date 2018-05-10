package com.sdyijia.wxapp.modules.sys.controller;

import com.alibaba.fastjson.JSONObject;
import com.sdyijia.wxapp.modules.sys.bean.ApiResult;


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
