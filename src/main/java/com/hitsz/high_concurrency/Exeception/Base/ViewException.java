package com.hitsz.high_concurrency.Exeception.Base;

import com.hitsz.high_concurrency.Result.CodeMsg;
import lombok.Data;

@Data
public class ViewException extends WebException {
    private int code;
    private String msg;
    private CodeMsg codeMsg;
    public ViewException(CodeMsg msg) {
        this.codeMsg = msg;
        this.code = msg.getCode();
        this.msg = msg.getMsg();
    }
}
