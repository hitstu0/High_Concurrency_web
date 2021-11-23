package com.hitsz.high_concurrency.Controller.Register;

import com.hitsz.high_concurrency.Data.Info.UserRegisterInfo;

import com.hitsz.high_concurrency.Result.CodeMsg;
import com.hitsz.high_concurrency.Result.Result;

import com.hitsz.high_concurrency.Service.RegisterService;
import org.apache.maven.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;
    /**注册页面请求重定向到静态文件*/
    @GetMapping
    public String registerPage() {
        return "redirect:/Register";
    }
    @PostMapping
    public Result<CodeMsg> register(Model model, UserRegisterInfo user) {
        return registerService.register(user);
    }
}
