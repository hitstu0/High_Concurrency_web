package com.hitsz.high_concurrency.Util;

public class CommonMethod {
    /*判断字符串是否是手机号*/
    public static boolean judgeMobile(String mobile) {
        try {
             Long.parseLong(mobile);
             return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
