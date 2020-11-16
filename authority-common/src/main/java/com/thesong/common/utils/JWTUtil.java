package com.thesong.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

/**
 * @Author thesong
 * @Date 2020/11/16 17:02
 * @Version 1.0
 * @Describe
 */
public class JWTUtil {


    /**
     * 解析accesskey的密文
     * @param accessKey
     * @param key
     * @return plain
     */
    public static Claims parseAccessKey(String accessKey, String key){
        if("".equals(accessKey)){
            return null;
        }
        try {
            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(key))
                    .parseClaimsJws(accessKey)
                    .getBody();
        }catch (Exception e){
            return null;
        }
    }

    public static String createAccessKey(Integer userId, String userName, String key, int expirSeconds){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);

        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .claim("userId", userId) // 设置载荷信息
                .claim("username",userName)
                .claim("age",23)
                .setExpiration(DateTime.now().plusMinutes(expirSeconds).toDate()) // 设置超时时间
                .signWith(signatureAlgorithm, signingKey);

        //生成JWT
        return builder.compact();
    }





}
