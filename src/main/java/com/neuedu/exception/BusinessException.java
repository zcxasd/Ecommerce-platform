package com.neuedu.exception;

import com.neuedu.businessconst.Const;

import javax.servlet.http.HttpSession;

/**
 * 自定义异常类，继承RuntimeException（特点：可以捕获也可以不捕获）
 * */

public class BusinessException extends RuntimeException {
    // 异常类信息
    private String msg;
    // 异常提示
    private String warn;
    // 跳转的页面url
    private String url;

    public BusinessException() {

    }

    private BusinessException(String msg, String warn, String url) {
        this.msg = msg;
        this.warn = warn;
        this.url = url;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getWarn() {
        return warn;
    }

    public void setWarn(String warn) {
        this.warn = warn;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //  封装异常
    public static BusinessException createException(HttpSession session,String msg,String warn,String url){
        BusinessException businessException = new BusinessException(msg,warn,url);
        session.setAttribute(Const.EXCEPTION,businessException);
        return businessException;
    }
}
