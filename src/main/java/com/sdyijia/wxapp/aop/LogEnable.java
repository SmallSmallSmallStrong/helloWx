package com.sdyijia.wxapp.aop;

import java.lang.annotation.*;

/**
 * 这里我们定义日志的开关量，类上只有这个值为true，这个类中日志功能才开启
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})//TODO 需确认引用
public @interface LogEnable {
    /**
     * 如果为true，则类下面的LogEvent启作用，否则忽略
     * @return
     */
    boolean logEnable() default true;
}