package com.hitsz.high_concurrency.Controller;

import com.hitsz.high_concurrency.Data.Order.Order;
import com.hitsz.high_concurrency.Data.Order.OrderInfo;
import com.hitsz.high_concurrency.Data.User;
import com.hitsz.high_concurrency.Exeception.Base.ViewException;
import com.hitsz.high_concurrency.Redis.RedisCommondFactory;
import com.hitsz.high_concurrency.Redis.Commond.RedisCommond;
import com.hitsz.high_concurrency.Redis.Key.GoodsKey;
import com.hitsz.high_concurrency.Redis.RedisServer.RedisService;
import com.hitsz.high_concurrency.Result.CodeMsg;
import com.hitsz.high_concurrency.Result.Result;
import com.hitsz.high_concurrency.Service.MiaoShaService;
import com.hitsz.high_concurrency.Service.OrderService;
import com.hitsz.high_concurrency.Validator.UserIdentify;
import com.hitsz.high_concurrency.Validator.ViewCount;
import com.hitsz.high_concurrency.Validator.ViewCurrent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private MiaoShaService miaoShaService;
    @Autowired
    private RedisService rService;

  

    @GetMapping
    public String getOrder() {
        return "Order.html";
    }
    
    @UserIdentify
    @PostMapping("/{path}")
    @ViewCount
    @ViewCurrent
    @ResponseBody
    public Result<CodeMsg> setOrder(User user, HttpServletRequest request,
                                  @RequestBody OrderInfo info,@PathVariable String path) throws Exception{
        if(user == null) throw new ViewException(CodeMsg.NOT_LOGIN);     
        info.setUserId(user.getId());
        //判断用户请求次数(接口防刷)
        boolean judge = miaoShaService.judgeUserRequestNum(user,info.getGoodsId());
        if(judge == false) throw new ViewException(CodeMsg.REQUEST_TOO_MUCH);
        //验证地址是否正确(接口隐藏)
        boolean correct = orderService.judgePath(info.getGoodsId(), user, path);
        if(correct == false) throw new ViewException(CodeMsg.URL_ERROR);
        return Result.success(orderService.setOrder(user,info));
    }
 
    //传入订单 id 进行支付
    @UserIdentify
    @PostMapping("/payOrder/{id}")
    @ResponseBody
    public Result<CodeMsg> payOrder(User user,HttpServletRequest request,
                                   @PathVariable int id) throws Exception {
        if(user == null) throw new ViewException(CodeMsg.NOT_LOGIN);
        //正常逻辑
        return Result.success(orderService.payOrder(id));
    }

    @UserIdentify
    @GetMapping("/path/{id}")
    @ResponseBody
    public Result<String> getPath(User user,HttpServletRequest request,
                                 @PathVariable int id) throws Exception{
        if(user == null) throw new ViewException(CodeMsg.NOT_LOGIN);
        return Result.success(orderService.getPath(id,user));
    }
}
