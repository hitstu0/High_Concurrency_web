package com.hitsz.high_concurrency.View;

import javax.servlet.http.HttpServletRequest;

import com.hitsz.high_concurrency.Data.User;
import com.hitsz.high_concurrency.Exeception.Base.ViewException;
import com.hitsz.high_concurrency.Result.CodeMsg;
import com.hitsz.high_concurrency.Result.Result;
import com.hitsz.high_concurrency.Validator.UserIdentify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/view")
public class viewController {
    @Autowired
    private viewService vService;
    @GetMapping
    public String getView() {
        return "View.html";
    }    

    @GetMapping("/data")
    @UserIdentify
    @ResponseBody
    public Result<viewData> getViewData(User user,HttpServletRequest request) {
        if(user == null) throw new ViewException(CodeMsg.NOT_LOGIN);
        return Result.success(vService.getData());
    }
}
