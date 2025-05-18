package com.ex.sas.Domain.User.Service;

import com.ex.sas.Domain.User.Entity.UserEntity;
import com.ex.sas.Domain.User.Repository.UserRepository;
import com.ex.sas.Error.CustomException;
import com.ex.sas.Error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //DB 에서 조회
        UserEntity userData = userRepository.findByUserId(username);
        System.out.println("찾은 사용자: " + userData.getUserId());
        // userData가 존재할 경우 일치하는 CustomUserDetails객체생성
        if(userData != null) {
            return new CustomUserDetails(userData);
        } else {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }
}
