package com.eastcom.ripple.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * 加密
 */
public final class Encrypt {

    //常量
    public static final long EXPIRE = 1000 * 60 * 60 * 24; //token过期时间
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO"; //秘钥

    /**
     * 生成加密后的token
     * @return 加密后的token
     */
    public String getToken(Long employeeId) {
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer("eastcomRipple")  // 发布者
                    .withIssuedAt(new Date())   // 生成签名的时间
                    .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE))   // 生成签名的有效期,小时
                    .withClaim("id", employeeId)
                    // 使用了HMAC256加密算法。
                    // APP_SECRET是用来加密数字签名的密钥。
                    .sign(Algorithm.HMAC256(APP_SECRET));
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        } catch (IllegalArgumentException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return token;
    }
}