package com.sdyijia.wxapp.aop.manager;

import com.sdyijia.wxapp.aop.bean.LogAdmModel;

/**
 * 定义日志处理的接口类ILogManager <br/>
 * 我们可以将日志存入数据库，也可以将日志发送到开中间件，如果redis, mq等等。<br/>
 * 每一种日志处理类都是此接口的实现类
 */
public interface ILogManager {
    /**
     * 日志处理模块
     * @param paramLogAdmBean
     */
    void dealLog(LogAdmModel paramLogAdmBean);
}
