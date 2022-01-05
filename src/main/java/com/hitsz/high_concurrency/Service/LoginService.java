package com.hitsz.high_concurrency.Service;

import com.hitsz.high_concurrency.Data.Info.UserLoginInfo;
import com.hitsz.high_concurrency.Data.User;
import com.hitsz.high_concurrency.Exeception.Base.ViewException;
import com.hitsz.high_concurrency.Mybatis.Base.UserBase;
import com.hitsz.high_concurrency.Mybatis.MabatisUtil;
import com.hitsz.high_concurrency.Redis.Key.UserKey;
import com.hitsz.high_concurrency.Redis.RedisService;
import com.hitsz.high_concurrency.Result.CodeMsg;
import com.hitsz.high_concurrency.Result.Result;
import com.hitsz.high_concurrency.Util.FinalValue;
import com.hitsz.high_concurrency.Util.MD5Util;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
public class LoginService {
    @Autowired
    private RedisService redisService;
    
    public User getUserByToken(String token) {
       if(token == null || token.length() == 0) return null;
       return redisService.get(UserKey.Login,token,User.class);
    }
    public User getUserByRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String token = null;
        for(Cookie c : cookies) {
            if(c.getName().equals(FinalValue.tokenName)) {
                token = c.getValue();
                break;
            }            
        }
    
        return getUserByToken(token);
    }
    
    public Result<CodeMsg> doLogin(HttpServletResponse response, UserLoginInfo info) {
        //验证用户
        SqlSession session = MabatisUtil.getSqlSession();
        User user = null;
        try {
            UserBase uBase = session.getMapper(UserBase.class);           
            user = uBase.getUserByMobile(Long.parseLong(info.getMobile()));
            if(user == null) throw new ViewException(CodeMsg.USER_NOT_EXISTS);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
        }
        //验证密码
        String tpass = MD5Util.getDBPassword(info.getPassword(),user.getSalt());        
        if(!tpass.equals(user.getPassword())) throw new ViewException(CodeMsg.PASSWORD_ERROR);
        //生成token
        String token = MD5Util.getToken(user.getMobile() + "",user.getPassword());
        Cookie cookie = new Cookie(FinalValue.tokenName,token); 
        response.addCookie(cookie);
        redisService.set(UserKey.Login,token,user);
        return Result.success(CodeMsg.SUCCESS);
    }
}
