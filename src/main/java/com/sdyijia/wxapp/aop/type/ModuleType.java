package com.sdyijia.wxapp.aop.type;

/**
 * 我们将日志抽象为以下两个类：功能模块和操作类型 使用枚举类定义功能模块类型ModuleType，如学生、用户模块
 */
public enum ModuleType {
    DEFAULT("1"), // 默认值
    STUDENT("2"),// 学生模块
    TEACHER("3"); // 用户模块

    private ModuleType(String index) {
        this.module = index;
    }

    private String module;

    public String getModule() {
        return this.module;
    }
}