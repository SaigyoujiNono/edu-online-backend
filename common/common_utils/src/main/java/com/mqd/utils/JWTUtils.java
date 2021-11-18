package com.mqd.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.validation.ObjectError;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTUtils {
    private final static String APP_SECRET = "5aSn5LqOMjU25L2N55qEaGFzaOWvhueggeeUqGJhc2U2NOeUn+aIkA==";
    private final static long EXPIRATION = 1000L*60*60*24;

    private static Key key;

    private static Key getKey(){
        if (key != null){
            return key;
        }
        byte[] encodedKey = java.util.Base64.getDecoder().decode(APP_SECRET);
        key = new SecretKeySpec(encodedKey, SignatureAlgorithm.HS256.getJcaName());
        return key;
    }

    /**
     * 生成jwt字符串
     * @param claims    需要在jwt存储的键值对
     * @return  返回字符串
     */
    public static String createJWT(Map<String,?> claims) {
        return Jwts.builder()
                .setHeaderParam("typ","jwt")
                .setHeaderParam("alg","HS256")
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .signWith(getKey())
                .compact();
    }

    /**
     * 解析JWT字符串
     */
    public static Claims parseJWT(String jwt){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())                 //设置签名的秘钥
                .build()
                .parseClaimsJws(jwt).getBody();
    }
}
