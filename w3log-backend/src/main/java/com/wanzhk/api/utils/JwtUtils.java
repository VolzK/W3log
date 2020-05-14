package com.wanzhk.api.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wanzhk.api.modules.vo.UserVo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt工具类
 *
 * @author wangzhiheng
 * <p>
 * 2020-05-14
 */
public class JwtUtils {

    public static Logger logger = LoggerFactory.getLogger(JwtUtils.class);


    // 密钥盐值
    public static final String TOKEN_SECRET = Base64.encodeBase64String("w3log jwt".getBytes());

    // 签发者
    public static final String IS_USER = "W3log Server";

    // 设置失效时间，15分钟
    private static final long EXPIRE_TIME = 15 * 60 * 1000;


    /**
     * 生成 Token
     *
     * @param user 用户对象
     * @return Token
     */
    public static String createToken(UserVo user) {
        // 1.header：构建头部信息
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("alg", "HS256"); // 加密算法
        headerClaims.put("type", "jwt");  // token类型

        // 签名 Signature,使用了HMAC256加密算法。
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + EXPIRE_TIME);

            // 3.组合 头部信息 和 载荷信息（withClaim） 再次加盐加密生成jwt token
            token = JWT.create()
                    .withHeader(headerClaims)                                   // 设置 头部信息 Header
                    .withClaim("username", user.getUsername())            // 设置 载荷信息
                    .withIssuer(IS_USER)                                        // 设置 载荷 jwt签发者 例如 服务器
                    .withSubject("W3log Jwt")                                   // 设置 载荷 签名的主题
                    .withAudience(user.getId())                                 // 设置 载荷 签名的观众 也可以理解谁接受签名的
                    .withIssuedAt(new Date())                                   // 设置 载荷 生成签名的时间
                    .withExpiresAt(expiresAt)                                   // 设置 载荷 签名过期的时间
                    .sign(Algorithm.HMAC256(TOKEN_SECRET));
            return token;
        } catch (Exception e) {
            logger.error("token生成失败：" + e.getMessage());
        }
        return null;
    }

    /**
     * 验证token
     *
     * @param token token
     * @return true：验证成功，false：验证失败
     */
    public static boolean verifyToken(String token) {
        try {
            // 通过密钥信息和签名的发布者的信息生成JWTVerifier (JWT验证类)
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET))
                    .withIssuer(IS_USER)
                    .build(); // Reusable verifier instance

            // 通过JWTVerifier 的verify()方法获取 token中的信息。
            DecodedJWT jwt = jwtVerifier.verify(token);
            return true;
        } catch (Exception e) {
            logger.error("token 验证失败：" + e.getMessage());
        }
        return false;
    }
}
