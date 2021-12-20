package com.hitsz.high_concurrency.Controller.Register;

import com.hitsz.high_concurrency.Data.Info.UserLoginInfo;
import com.hitsz.high_concurrency.Data.User;
import com.hitsz.high_concurrency.Result.CodeMsg;
import com.hitsz.high_concurrency.Result.Result;
import com.hitsz.high_concurrency.Service.LoginService;
import com.hitsz.high_concurrency.Validator.UserIdentify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class loginController {
    @Autowired
    private LoginService loginService;
    @GetMapping
    public String getlogin() {
        return "Login.html";
    }
    @PostMapping
    @ResponseBody
    public Result<CodeMsg> doLogin(HttpServletResponse response, @Validated @RequestBody UserLoginInfo info) {
        return loginService.doLogin(response,info);
    }
    @UserIdentify
    @GetMapping("/queryUser")
    public User getLoginUser(User user, HttpServletRequest request){
        return user;
    }
}
