package com.hitsz.high_concurrency.Data.Goods;

import lombok.Data;

@Data
public class Goods {
    private int id;
    private int details;
    private String name;
    private double price;
    private int store;
    private String image;
}
