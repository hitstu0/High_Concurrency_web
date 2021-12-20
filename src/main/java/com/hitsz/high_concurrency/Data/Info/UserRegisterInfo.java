package com.hitsz.high_concurrency.Data.Info;

import com.hitsz.high_concurrency.Validator.isMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
@Data
public class UserRegisterInfo {
    //当注解效验失败时会自动返回报文，实体体中包含错误码和message
    @NotBlank(message = "名字不能为空")
    private String name;

    @NotBlank(message = "密码不能为空")
    //@Length(min= 32, max = 32,message = "前端页面错误")
    private String password;

    @NotBlank(message = "手机号不能为空")
    //@isMobile
    //@Length(min = 11,max=11,message = "手机号码长度错误")
    private String mobile;
}
