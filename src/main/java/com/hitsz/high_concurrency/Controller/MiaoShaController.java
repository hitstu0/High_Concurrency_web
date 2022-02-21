package com.hitsz.high_concurrency.Controller;

import com.hitsz.high_concurrency.Data.Goods.MiaoShaInfo;
import com.hitsz.high_concurrency.Result.Result;
import com.hitsz.high_concurrency.Service.GoodsService;
import com.hitsz.high_concurrency.Service.MiaoShaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/miaosha")
public class MiaoShaController {
    @Autowired
    private MiaoShaService service;
    
    @GetMapping("/{id}")
    //获取秒杀信息，如果秒杀未开始则前端按钮置为灰色
    public Result<MiaoShaInfo> getMiaoShaInfo(@PathVariable int id) throws Exception{
        return service.getMiaoShaInfo(id);
    }

    @GetMapping("/downCount/{id}")
    //获取秒杀倒计时,客户端根据该倒计时和误差修正获得真正的倒计时
    public Result<Long> getMiaoShaDownCount(@PathVariable int id) throws Exception {
        return service.getMiaoShaDownCount(id);
    }
}
