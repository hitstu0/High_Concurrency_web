package com.hitsz.high_concurrency.Service;

import com.hitsz.high_concurrency.Data.Goods.Goods;
import com.hitsz.high_concurrency.Data.Order.Order;
import com.hitsz.high_concurrency.Data.Order.OrderInfo;
import com.hitsz.high_concurrency.Data.User;
import com.hitsz.high_concurrency.Exeception.Base.ViewException;
import com.hitsz.high_concurrency.Mybatis.Base.GoodsBase;
import com.hitsz.high_concurrency.Mybatis.Base.OrderBase;
import com.hitsz.high_concurrency.Mybatis.MabatisUtil;
import com.hitsz.high_concurrency.Result.CodeMsg;

import org.apache.ibatis.session.SqlSession;

import org.springframework.stereotype.Service;

@Service
public class OrderService {   
   
    public Order setOrder(User user,OrderInfo info) {
        if(user == null) throw new ViewException(CodeMsg.NOT_LOGIN);              
        //下订单
        SqlSession session = MabatisUtil.getSqlSession();
        try {
            OrderBase base = session.getMapper(OrderBase.class);
            base.addOrder(info);        
            //生成订单信息
            Order order = new Order();
            GoodsBase goodsBase = session.getMapper(GoodsBase.class);
            Goods goods = goodsBase.getGoods(info.getGoodsId());            
            order.setGoodsName(goods.getName());
            order.setImage(goods.getImage());
            order.setNums(info.getNums());
            order.setPrice(goods.getPrice());
            order.setStore(goods.getStore());
            order.setUserName(user.getName());            
            return order;
        } finally {
            session.close();
        }
        
    }
}
