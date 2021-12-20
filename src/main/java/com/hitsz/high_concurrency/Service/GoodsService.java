package com.hitsz.high_concurrency.Service;

import com.hitsz.high_concurrency.Data.Goods.Goods;
import com.hitsz.high_concurrency.Data.Goods.GoodsDetail;
import com.hitsz.high_concurrency.Mybatis.Base.GoodsBase;
import com.hitsz.high_concurrency.Mybatis.MabatisUtil;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {
    public List<Goods> getGoodsList() {
        SqlSession session = MabatisUtil.getSqlSession();
        try {
            GoodsBase goodsBase = session.getMapper(GoodsBase.class);
            return goodsBase.getGoodsList();
        } finally {
            session.close();
        }
    }
    public GoodsDetail getGoodsDetail(int id) {
        SqlSession session = MabatisUtil.getSqlSession();
        try {
            GoodsBase goodsBase = session.getMapper(GoodsBase.class);
            return goodsBase.getGoodsDetail(id);
        } finally {
            session.close();
        }
    }
}
