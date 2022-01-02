package com.hitsz.high_concurrency.View;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class viewService {
    private viewData data = new viewData();
    public void addTotal() {
        data.getTotalCount().incrementAndGet();
    }
    
    public void addCurrent() {
        data.getHandleCount().incrementAndGet();
    }

    public viewData getData() {
        return data;
    }
}
