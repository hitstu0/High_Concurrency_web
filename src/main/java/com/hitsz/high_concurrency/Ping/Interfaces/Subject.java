package com.hitsz.high_concurrency.Ping.Interfaces;

public interface Subject {
    public void addObserver(Observer o);
    public void deleteObserver(Observer o);
}
