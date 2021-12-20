package com.hitsz.high_concurrency.Controller.Register;

import com.hitsz.high_concurrency.Data.Goods.GoodsDetail;
import com.hitsz.high_concurrency.Exeception.Base.ViewException;
import com.hitsz.high_concurrency.Result.CodeMsg;
import com.hitsz.high_concurrency.Result.Result;
import com.hitsz.high_concurrency.Service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("/goodsDetail")
public class GoodsDetailController {
    @Autowired
    private GoodsService goodsService;
    @GetMapping
    public String getGoodsDetail() {
        return "GoodsDetail.html";
    }
    @GetMapping("/{id}")
    public Result<GoodsDetail> getGoodsDetailById(@PathVariable int id) {
        GoodsDetail goodsDetail = goodsService.getGoodsDetail(id);
        if(goodsDetail == null) throw new ViewException(CodeMsg.GOODS_NOT_FOUND);
        return Result.success(goodsDetail);
    }
}
