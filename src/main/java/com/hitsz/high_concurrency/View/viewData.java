package com.hitsz.high_concurrency.View;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.Data;

@Data
public class viewData {
    private AtomicInteger totalCount = new AtomicInteger();
    private AtomicInteger handleCount = new AtomicInteger();
}
