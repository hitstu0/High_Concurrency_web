package com.hitsz.high_concurrency.Redis.Key;
import com.hitsz.high_concurrency.Redis.prefix.abstractPrefix;
public class UserKey extends abstractPrefix{
    
    private UserKey(String pre,int expired) {
        super(pre,expired);
    }
    private UserKey(String pre){
        super(pre);
    }
    public static UserKey Login = new UserKey("login",60*24);
    public static UserKey RequestNum = new UserKey("requestNum",5);
}
