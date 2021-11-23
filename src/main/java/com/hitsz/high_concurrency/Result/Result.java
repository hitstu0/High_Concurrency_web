package com.hitsz.high_concurrency.Result;

//返回消息统一封装
//泛型类，T 表示 msg 的类型
/**
 * 分成Result和CodeMsg的原因
 * 1.解耦返回结果包装和信息
 * 
 */
public class Result<T> {
    private int code;
    private String msg;
    private T data;
    private Result(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }
    private Result(CodeMsg msg) {
        this(msg.getCode(),msg.getMsg());        
    }
    private Result(T data) {
        this(0, "成功");
        this.data = data;
    }  
    public static <T> Result<T> success(T msg) {
        Result<T> result = new Result<T>(msg);
        return result;    
    }
    public static Result<CodeMsg> error(CodeMsg msg) {return new Result<>(msg);}
    public static Result<CodeMsg> success(CodeMsg msg) {
        return new Result<>(msg);
    }
}
