package com.hitsz.high_concurrency.Mybatis.Base;

import com.hitsz.high_concurrency.Data.User;

public interface UserBase {
    public User getUserById(int id);
    public User getUserByMobile(long mobile);
    public void addUser(User u);
}
