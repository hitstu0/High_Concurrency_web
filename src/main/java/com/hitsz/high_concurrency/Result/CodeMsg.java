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
    
    //登陆相关2xx
    public static CodeMsg USER_NOT_EXISTS = new CodeMsg(200,"登陆用户不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(201,"密码错误");
    
    //商品相关3xx
    public static CodeMsg GOODS_NOT_FOUND = new CodeMsg(300,"商品不存在");
    
    //订单相关4xx
    public static CodeMsg NOT_LOGIN = new CodeMsg(400,"用户未登陆");
}
