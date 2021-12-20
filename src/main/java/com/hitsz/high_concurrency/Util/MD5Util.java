package com.hitsz.high_concurrency.Util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    private static String md5(String src) {        
        return DigestUtils.md5Hex(src);
    }
    private static String combinePassAndSalt(String pass,String salt){
        return salt.charAt(0) + salt.charAt(1) + pass;
    }
    public static String getRandomsalt(String password) {        
        return md5(password);
    }
    public static String getDBPassword(String password,String salt){
        return md5(combinePassAndSalt(password,salt));
    }
    public static String getToken(String a,String b) {
        return md5(a + b);
    }
}
