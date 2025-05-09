package com.ex.sas.Domain.User.Service;

import com.ex.sas.Domain.User.DTO.JoinDTO;
import com.ex.sas.Domain.User.Entity.UserEntity;
import com.ex.sas.Domain.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    //JWT로 해당 유저 불러오기
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserEntity user = userDetails.getUserEntity();
        //영속성 컨텍스트 위반 문제로 DB에서 한번더 조회 필요

        UserEntity user2 =  userRepository.findByUserId(user.getUserId());
        return user2;
    }

    public void joinProcess(JoinDTO joinDTO){
        String userName = joinDTO.getUserName();
        String password = joinDTO.getUserPassword();
        String introduce = joinDTO.getUserIntroduce();
        String userId = joinDTO.getUserId();

        Boolean isExist = userRepository.existsByUserId(userId);

        if (isExist){
            return;
        }

        UserEntity data = new UserEntity();
        data.setUser_name(userName);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setUser_introduce(introduce);
        data.setUserId(userId);

        userRepository.save(data);
    }

    //프로필 불러오기
    public JoinDTO getProfile(){
        //JWT 유저 정보 가져오기
        UserEntity user = getCurrentUser();
        JoinDTO joinDTO = new JoinDTO();

        joinDTO.setUserName(user.getUser_name());
        joinDTO.setUserIntroduce(user.getUser_introduce());

        return joinDTO;
    }

    //프로필 수정
    public void putProfile(JoinDTO request){
        UserEntity user = getCurrentUser();

        //유저 비밀번호 및 정보 변경
        //user.setPassword(bCryptPasswordEncoder.encode(request.getUserPassword()));
        user.setUser_introduce(request.getUserIntroduce());
        //user.setUserId(request.getUserId());
        user.setUser_name(request.getUserName());

        userRepository.save(user);
    }



}
