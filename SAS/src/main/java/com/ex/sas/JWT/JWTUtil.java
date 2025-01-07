package com.ex.sas.JWT;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

    //비밀키 저장 필드
    private SecretKey secretKey;

    //application.properties에 정의 해놓은 비밀키 변수 가져와서 비밀키 생성
    public JWTUtil(@Value("S{spring.jwt.secret}") String secret){
        this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    //JWT 토큰의 유저 ID 검증을 진행할 메소드
    public String getUsername(String token){
        return
                Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .get("username", String.class);
    }

//    public String getRole(String token){
//        return
//        Jwts.parser()
//        .verifyWith(secretKey)
//        .build().parseSignedClaims(token)
//        .getPayload()
//        .get("role", String.class);
//    }

    //JWT 토큰이 만료되었는지 검증을 진행할 메소드
    public Boolean isExpired(String token){
        return
                Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getExpiration()
                        .before(new Date());
    }

    //토큰 생성 메소드
    public String createJWT (String username, Long expiredMs){
        return Jwts.builder()
                .claim("username", username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }

}
