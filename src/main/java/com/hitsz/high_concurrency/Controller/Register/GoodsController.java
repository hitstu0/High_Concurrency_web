package com.hitsz.high_concurrency.Controller.Register;

import com.hitsz.high_concurrency.Data.Goods.Goods;
import com.hitsz.high_concurrency.Result.Result;
import com.hitsz.high_concurrency.Service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Controller
@RequestMapping
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @GetMapping("/goods")
    public String getGoodsView() {
        return "Goods.html";
    }
    @GetMapping("/goodsInfo")
    public Result<List<Goods>> getGoodsInfo() {
        List<Goods> list = goodsService.getGoodsList();
        return Result.success(list);
    }
}
