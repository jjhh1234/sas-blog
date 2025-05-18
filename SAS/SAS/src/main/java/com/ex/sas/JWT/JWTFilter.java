package com.ex.sas.JWT;

import com.ex.sas.Domain.User.Entity.UserEntity;
import com.ex.sas.Domain.User.Service.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //request에서 Authorization 헤더를 읽어옴
        String authorization = request.getHeader("Authorization");

        //토큰이 비어있거나 Bearer 로 시작하지 않는다면 token null 문자열 반횐
        if (authorization == null || !authorization.startsWith("Bearer ")) {

            System.out.println("token null");
            filterChain.doFilter(request, response);

            return;
        }

        System.out.println("authorization now");

        //Bearer 부분 제거 후 순수 토큰만 획득
        String token = authorization.split(" ")[1];

        //토큰소멸시간 검증
        if (jwtUtil.isExpired(token)) {
            System.out.println("token expired");
            filterChain.doFilter(request, response);

            return;
        }

        //token에서 username 획듯
        String username =  jwtUtil.getUsername(token);

        //userEntity 생성해서 값 set
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(username);
        userEntity.setPassword("temppassword");
        System.out.println(userEntity.getUserId());

        //UserDetails 에 회원정보 객체 담기
        CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

        //스프링 시큐리티 인증토큰 생성
        Authentication AuthToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(AuthToken);
        filterChain.doFilter(request, response);
    }
}
