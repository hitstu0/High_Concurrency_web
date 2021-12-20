package com.hitsz.high_concurrency.Data.Order;

import lombok.Data;

@Data
public class Order {
    private int id;
    //商品信息    
    private String goodsName;
    private double price;
    private int store;
    private String image;
    //用户名字
    private String userName;
    //商品数量
    private int nums;
}
