package com.hitsz.high_concurrency.Mybatis.Base;

import com.hitsz.high_concurrency.Data.Goods.MiaoShaInfo;

public interface MiaoShaBase {
    MiaoShaInfo getMiaoShaInfo(int goodsId);
}
