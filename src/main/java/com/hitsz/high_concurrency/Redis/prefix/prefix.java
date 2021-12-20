package com.hitsz.high_concurrency.Redis.prefix;

public interface prefix {
    int getExpiredTime();
    String getPrefix();
}
