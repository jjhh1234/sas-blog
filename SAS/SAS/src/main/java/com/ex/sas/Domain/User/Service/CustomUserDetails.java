package com.ex.sas.Domain.User.Service;

import com.ex.sas.Domain.User.Entity.UserEntity;
import lombok.Generated;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserEntity userEntity;

    //Role 값을 반환하는 메소드
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //나는 Role을 따로 Entity에 정의하지 않앆기 때문에 그냥 빈 리스트 반환함
        return Collections.emptyList();
    }

    //userEntity 반환
    public UserEntity getUserEntity() {
        return userEntity;
    }

    //비밀번호 반환
    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    //userId 반환
    @Override
    public String getUsername() {
        return userEntity.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
