package com.hitsz.high_concurrency.Data.Info;
import com.hitsz.high_concurrency.Validator.isMobile;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserLoginInfo {
    //@isMobile
    @NotBlank(message = "手机号不能为空")
    private String mobile;
    @NotBlank(message = "密码不能为空")
    private String password;
}
