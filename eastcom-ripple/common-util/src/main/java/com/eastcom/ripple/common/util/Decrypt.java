package com.eastcom.ripple.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * 解密
 */
@Slf4j
public final class Decrypt {

    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO"; //秘钥
    /**
     * 先验证token是否被伪造，然后解码token。
     * @param token 字符串token
     * @return 解密后的DecodedJWT对象，可以读取token中的数据。
     */
    public boolean deToken(String token) {
        if(StringUtils.isEmpty(token)) return false;
        try {
            // 使用了HMAC256加密算法。
            // APP_SECRET是用来加密数字签名的密钥。
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(APP_SECRET))
                    .withIssuer("eastcomRipple")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            //打印存入token的数据---员工id
            log.info("token:::"+jwt.getClaim("id").asLong());
        } catch (JWTVerificationException | IllegalArgumentException | UnsupportedEncodingException e){
            //Invalid signature/claims
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
