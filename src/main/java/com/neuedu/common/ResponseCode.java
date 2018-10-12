package com.neuedu.common;

// 创建一个枚举类
public enum ResponseCode {

    // 成功
    SUCCESS(0,"成功"),
    // 需要登录
    NEED_LOGIN(1,"需要登录"),
    // 没有权限
    NO_PERMISSION(2,"无权限"),
    // 获取子类别categoryId必传
    GETSUBCATEGORY_NEED_CATEGORYID(3,"categoryId必须"),
    // 获取子类别categoryName必传
    GETSUBCATEGORY_NEED_CATEGORYNAME(4,"类别名称必须"),
    NEED_PRODUCT(5,"商品参数必须"),
    NEED_PRODUCT_STATUS(7,"商品status参数必须"),
    PRODUCT_OFFLINE(8,"商品不存在或者已经下架"),
    NUMBER(9,"商品最少一件"),
    NEED_ORDERNO(10,"需要订单号"),
    NOT_FOUNT_ORDERNO(11,"没有该订单号"),
    CART_EMPTY(12,"购物车空"),
    PRODUCT_OFFLINE_NOT_EXISTS(13,"商品下架或者不存在"),
    PRODUCT_STOCK_LESS(14,"商品库存不足"),
    ORDER_NOT_CANCEL(15,"该订单无法取消"),
    CARE_NOT_FOUND_PRODUCT(16,"购物车中无商品"),
    // 接口返回失败
    FAIL(100,"失败");

    private int code;
    private String msg;
    private ResponseCode(int code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
