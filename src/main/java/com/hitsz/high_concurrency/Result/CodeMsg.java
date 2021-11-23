package com.hitsz.high_concurrency.Result;

import lombok.Data;

@Data
//返回消息统一定义文件
public class CodeMsg {
    private int code;
    private String msg;
    private CodeMsg(int code,String msg) {
       this.code = code;
       this.msg = msg;
    }
    public static CodeMsg SUCCESS = new CodeMsg(0, "成功");
    //注册相关1xx
    public static CodeMsg USER_EXISTS = new CodeMsg(100,"注册用户已存在");
}
