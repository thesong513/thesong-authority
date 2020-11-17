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
    protected static String key = "thesong";

    /**
     * 解析accesskey的密文
     *
     * @param accessKey
     * @return plain
     */
    public static Claims parseAccessKey(String accessKey) {
        if ("".equals(accessKey)) {
            return null;
        }
        try {
            return Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(key))
                    .parseClaimsJws(accessKey)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * @param username
     * @param roles
     * @param expirDays
     * @return 密文
     */

    public static String createAccessKey(String username, String roles, int expirDays) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成签名密钥
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(key);

        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //添加构成JWT的参数
        JwtBuilder builder = Jwts.builder()
                .claim("username", username)
                .claim("roles", roles)
                .setExpiration(DateTime.now().plusDays(expirDays).toDate()) // 设置超时时间
                .signWith(signatureAlgorithm, signingKey);

        //生成JWT
        return builder.compact();
    }


}
