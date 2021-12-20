package com.hitsz.high_concurrency.Mybatis.Base;

import com.hitsz.high_concurrency.Data.Goods.Goods;
import com.hitsz.high_concurrency.Data.Goods.GoodsDetail;

import java.util.List;

public interface GoodsBase {
    List<Goods> getGoodsList();   
    Goods getGoods(int id);
    GoodsDetail getGoodsDetail(int id);
}
