package com.hitsz.high_concurrency.Data.Order;

import lombok.Data;

@Data
public class OrderInfo {
    private int id;
    private int goodsId;
    private int userId;
    private int nums;
}
