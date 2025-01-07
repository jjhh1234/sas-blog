package com.ex.sas.Domain.User.Service;

import com.ex.sas.Domain.User.DTO.JoinDTO;
import com.ex.sas.Domain.User.Entity.UserEntity;
import com.ex.sas.Domain.User.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void joinProcess(JoinDTO joinDTO){
        String userName = joinDTO.getUserName();
        String password = joinDTO.getUserPassword();
        String introduce = joinDTO.getUserIntroduce();
        String userId = joinDTO.getUserId();

        Boolean isExist = userRepository.existsById(userId);

        if (isExist){
            return;
        }

        UserEntity data = new UserEntity();
        data.setUser_name(userName);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setUser_introduce(introduce);
        data.setId(userId);

        userRepository.save(data);
    }

}
