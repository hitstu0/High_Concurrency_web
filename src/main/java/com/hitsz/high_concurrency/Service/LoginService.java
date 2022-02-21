package com.hitsz.high_concurrency.Service;

import com.hitsz.high_concurrency.Data.Info.UserLoginInfo;
import com.hitsz.high_concurrency.Data.User;
import com.hitsz.high_concurrency.Exeception.Base.ViewException;
import com.hitsz.high_concurrency.Mybatis.Base.UserBase;
import com.hitsz.high_concurrency.Redis.RedisCommondFactory;
import com.hitsz.high_concurrency.Redis.Commond.RedisCommond;
import com.hitsz.high_concurrency.Redis.Key.UserKey;
import com.hitsz.high_concurrency.Redis.RedisServer.RedisService;
import com.hitsz.high_concurrency.Mybatis.MabatisUtil;
import com.hitsz.high_concurrency.Result.CodeMsg;
import com.hitsz.high_concurrency.Result.Result;
import com.hitsz.high_concurrency.Util.FinalValue;
import com.hitsz.high_concurrency.Util.MD5Util;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
public class LoginService {
    
    private ConcurrentHashMap<String,User> map = new ConcurrentHashMap<>(2000);

    public User getUserByToken(String token) throws Exception {
       if(token == null || token.length() == 0) return null;
       return map.get(token);
    }
    public User getUserByRequest(HttpServletRequest request) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(cookies == null) return null;
        String token = null;
        for(Cookie c : cookies) {
            if(c.getName().equals(FinalValue.tokenName)) {
                token = c.getValue();
                break;
            }            
        }    
        return getUserByToken(token);
    }
    
    public Result<CodeMsg> doLogin(HttpServletResponse response, UserLoginInfo info) throws Exception {
        //验证用户
        SqlSession session = MabatisUtil.getSqlSession();
        User user = null;
        try {
            UserBase uBase = session.getMapper(UserBase.class);           
            user = uBase.getUserByMobile(Long.parseLong(info.getMobile()));
            if(user == null) throw new ViewException(CodeMsg.USER_NOT_EXISTS);
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
        map.put(token,user);
        return Result.success(CodeMsg.SUCCESS);
    }
}
