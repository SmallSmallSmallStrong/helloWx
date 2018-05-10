package com.sdyijia.wxapp.aop;

import com.sdyijia.wxapp.aop.type.EventType;
import com.sdyijia.wxapp.aop.type.ModuleType;

import java.lang.annotation.*;

/**
 * 这里定义日志的详细内容。<br />
 * 如果此注解注解在类上，则这个参数做为类全部方法的默认值。<br />
 * 如果注解在方法上，则只对这个方法启作用<br />
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD, ElementType.TYPE})
public @interface LogEvent {
    ModuleType module() default ModuleType.DEFAULT; // 日志所属的模块

    EventType event() default EventType.DEFAULT; // 日志事件类型

    String desc() default ""; // 描述信息
}
