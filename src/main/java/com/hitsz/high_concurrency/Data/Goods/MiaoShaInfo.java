package com.hitsz.high_concurrency.Data.Goods;

import java.sql.Date;

import lombok.Data;

@Data
public class MiaoShaInfo {
    private int nums;
    private double price;
    private Date beginTime;
    private Date endTime;
}
