package com.ex.sas.Domain.User.Service;

import com.ex.sas.Domain.User.Entity.UserEntity;
import com.ex.sas.Domain.User.Repository.UserRepository;
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
        UserEntity userData = userRepository.findById(username);

        // userData가 존재할 경우 일치하는 CustomUserDetails객체생성
        if(userData != null) {
            return new CustomUserDetails(userData);
        }

        return null;
    }
}
