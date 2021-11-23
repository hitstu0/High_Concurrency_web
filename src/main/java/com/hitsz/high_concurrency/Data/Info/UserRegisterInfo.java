package com.hitsz.high_concurrency.Data.Info;

import lombok.Data;

@Data
public class UserRegisterInfo {
    private String name;
    private String password;
    private long mobile;
}
