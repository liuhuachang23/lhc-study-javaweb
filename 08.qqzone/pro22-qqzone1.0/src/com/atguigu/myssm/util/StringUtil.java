package com.atguigu.myssm.util;

/**
 * @Author: 刘华昌
 * @DATE: 2022/6/9 19:06 星期四
 * @Operating:
 * @Description:
 */
public class StringUtil {
    //判断字符串为 null 或者 ""
    public static boolean isEmpty(String str){
        return str==null || "".equals(str);
    }

    //判断字符串不为 空 或者 ""
    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }

}
