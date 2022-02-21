package com.hitsz.high_concurrency.Service;

import com.hitsz.high_concurrency.Data.Info.UserRegisterInfo;
import com.hitsz.high_concurrency.Data.User;

import com.hitsz.high_concurrency.Exeception.Base.ViewException;
import com.hitsz.high_concurrency.Mybatis.Base.UserBase;
import com.hitsz.high_concurrency.Mybatis.MabatisUtil;

import com.hitsz.high_concurrency.Result.CodeMsg;
import com.hitsz.high_concurrency.Result.Result;
import com.hitsz.high_concurrency.Util.MD5Util;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import redis.clients.jedis.exceptions.JedisConnectionException;

@Service
public class RegisterService {    
    public Result<CodeMsg> register(UserRegisterInfo user) throws ViewException,JedisConnectionException{
        //验证用户是否存在
        SqlSession sqlSession = MabatisUtil.getSqlSession();
        try {
            UserBase userBase = sqlSession.getMapper(UserBase.class);
            User user1 = userBase.getUserByMobile(Long.parseLong(user.getMobile()));
            if(user1 != null) throw new ViewException(CodeMsg.USER_EXISTS);
           //如果不存在，将密码进行第二次MD5
           String vuePass = user.getPassword();
           String salt = MD5Util.getRandomsalt(vuePass).substring(0,10);
           String dpPass = MD5Util.getDBPassword(vuePass,salt);
           //存入数据库
            User u = new User();
            u.setMobile(Long.parseLong(user.getMobile()));      
            u.setName(user.getName());
            u.setPassword(dpPass);
            u.setSalt(salt);
            userBase.addUser(u);          
            sqlSession.commit();           
        } finally {
            //即使 try 块中有返回语句，在返回前也会执行 finally 块
            sqlSession.close();
        }       
        return Result.success(CodeMsg.SUCCESS);
    }
}
