package com.hitsz.high_concurrency.Util;

import java.util.concurrent.atomic.AtomicBoolean;

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
   
    public static final int BitMapLength = 1000;

    public static int hashOne(Object o) {
       int hash = o.hashCode();
       hash = (hash >>> 16) ^ hash;
       hash = (hash >>> 8) ^ hash;
       return hash % BitMapLength;
    }
    public static int hashTwo(Object o) {
      int hash = o.hashCode();
      hash = hash * hash;
      return hash % BitMapLength;
    }
    public static int hashThree(Object o) {
      int hash = o.hashCode();
      hash += 3 * hash;
      return hash % BitMapLength;
    }
}
