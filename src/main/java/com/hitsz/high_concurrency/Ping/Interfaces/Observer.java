package com.hitsz.high_concurrency.Ping.Interfaces;

import org.apache.rocketmq.client.exception.MQClientException;

public interface Observer {
    public void doWhenSuccess();
    public void doWhenFailOnce();
    public void doWhenShutDown();
}
