package com.neuedu.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    private static String DATEFORMAT="yyyy-MM-dd HH:mm:ss";

    // 将date转成字符串格式
    public static String dateToString(Date date,String dateformat){
        if (date==null){
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateformat);
        String str = simpleDateFormat.format(date);
        return str;
    }

    // 将date转成字符串格式
    public static String dateToString(Date date){
        if (date==null){
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATEFORMAT);
        String str = simpleDateFormat.format(date);
        return str;
    }

    // 将字符串转日期
    public static Date stringToDate(String datestr){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATEFORMAT);
        try {
            simpleDateFormat.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 将字符串转日期
    public static Date stringToDate(String datestr,String formate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formate);
        try {
            simpleDateFormat.parse(datestr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
