package com.hitsz.high_concurrency.Controller;



import com.hitsz.high_concurrency.Data.Info.UserRegisterInfo;

import com.hitsz.high_concurrency.Result.CodeMsg;
import com.hitsz.high_concurrency.Result.Result;

import com.hitsz.high_concurrency.Service.RegisterService;
import org.apache.maven.model.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private RegisterService registerService;

    /**注册页面*/
    @GetMapping
    public String registerPage() {      
        return "Register.html";
    }


    @PostMapping
    @ResponseBody
    //controller 返回的对象必须包含 get 和 set 方法
    public Result<CodeMsg> register(Model model,@Validated @RequestBody UserRegisterInfo user) {
        return registerService.register(user);
        
    }
}
