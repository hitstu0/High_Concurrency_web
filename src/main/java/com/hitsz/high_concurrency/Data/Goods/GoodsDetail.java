package com.hitsz.high_concurrency.Data.Goods;

import lombok.Data;

@Data
public class GoodsDetail {    
    private int id;
    private int relateId;
    private String name;
    private double price;
    private int store;
    private String image;
    private String description;
    private String maker;
}
