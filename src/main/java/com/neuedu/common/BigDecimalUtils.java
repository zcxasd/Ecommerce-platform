package com.neuedu.common;

import java.math.BigDecimal;

/*
* 价格计算通用工具类
* */
public class BigDecimalUtils {

    //加法
    public static BigDecimal add(BigDecimal b1,BigDecimal b2){
        return b1.add(b2);
    }
    //减法
    public static BigDecimal sub(BigDecimal b1,BigDecimal b2){
        return b1.subtract(b2);
    }
    //乘法
    public static BigDecimal multiply(BigDecimal b1,BigDecimal b2){
        return b1.multiply(b2);
    }
    //除法(保留两位小数，四舍五入)
    public static BigDecimal divide(BigDecimal b1,BigDecimal b2){
        return b1.divide(b2,2,BigDecimal.ROUND_HALF_UP);
    }
}
