package com.sdyijia.wxapp.util.page;


public class SortDto {

    //排序方式
    private String orderType;

    //排序字段
    private String orderField;

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    /**
     * @param orderType  排序方式
     * @param orderField 排序字段
     */
    public SortDto(String orderField, String orderType) {
        this.orderType = orderType;
        this.orderField = orderField;
    }

    /**
     * @param orderField 排序字段  默认正序
     */
    public SortDto(String orderField) {
        this.orderField = orderField;
        this.orderType = "asc";
    }
}
