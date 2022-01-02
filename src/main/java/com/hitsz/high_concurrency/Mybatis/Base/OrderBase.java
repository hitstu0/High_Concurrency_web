package com.hitsz.high_concurrency.Mybatis.Base;

import com.hitsz.high_concurrency.Data.Order.OrderInfo;

public interface OrderBase {
    public void addOrder(OrderInfo info);
}
