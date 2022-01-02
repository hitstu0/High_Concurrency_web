package com.hitsz.high_concurrency.Controller;

import com.hitsz.high_concurrency.Data.Order.Order;
import com.hitsz.high_concurrency.Data.Order.OrderInfo;
import com.hitsz.high_concurrency.Data.User;
import com.hitsz.high_concurrency.Exeception.Base.ViewException;
import com.hitsz.high_concurrency.Result.CodeMsg;
import com.hitsz.high_concurrency.Result.Result;
import com.hitsz.high_concurrency.Service.OrderService;
import com.hitsz.high_concurrency.Validator.UserIdentify;
import com.hitsz.high_concurrency.Validator.ViewCount;
import com.hitsz.high_concurrency.Validator.ViewCurrent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public String getOrder() {
        return "Order.html";
    }
    @UserIdentify
    @PostMapping
    @ViewCount
    @ViewCurrent
    public Result<Order> setOrder(User user, HttpServletRequest request,
                                  @RequestBody OrderInfo info) {
        if(user == null) throw new ViewException(CodeMsg.NOT_LOGIN);
        
        return Result.success(orderService.setOrder(user,info));
    }
}
