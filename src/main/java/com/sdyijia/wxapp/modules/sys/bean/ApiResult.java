package com.sdyijia.wxapp.modules.sys.bean;

import com.alibaba.fastjson.JSON;

public class ApiResult {
    private Integer status;
    private Object data;


    public ApiResult(Integer status, Object data) {
        this.status = status;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toJSONString() {
        return JSON.toJSONString(this);
    }


    @Override
    public String toString() {
        return toJSONString();
    }
}
