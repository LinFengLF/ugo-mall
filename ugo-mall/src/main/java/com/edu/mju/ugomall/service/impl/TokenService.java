package com.edu.mju.ugomall.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.edu.mju.ugomall.entity.Admin;
import com.edu.mju.ugomall.entity.User;
import com.edu.mju.ugomall.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author 67072
 */
@Service
public class TokenService {

    public String getUserToken(User user) {
        Date start = new Date();
        //一小时有效时间
        long currentTime = System.currentTimeMillis() + 60 * 60 * 1000;
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience(user.getUserId().toString()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(user.getUserPwd()));
        return token;
    }


    public String getAdminToken(Admin admin) {
        Date start = new Date();
        //一小时有效时间
        long currentTime = System.currentTimeMillis() + 60 * 60 * 1000;
        Date end = new Date(currentTime);
        String token = "";

        token = JWT.create().withAudience(admin.getId().toString()).withIssuedAt(start).withExpiresAt(end)
                .sign(Algorithm.HMAC256(admin.getPassword()));
        return token;
    }


}