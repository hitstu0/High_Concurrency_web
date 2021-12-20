package com.hitsz.high_concurrency.Redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
//负责引入配置文件中的值
@ConfigurationProperties(prefix="redis")
public class RedisConfig {
    private String host;
    private int port;
    private int timeOut;
    private int maxActive;
    private int maxWait;
    private int maxIdle;
}
