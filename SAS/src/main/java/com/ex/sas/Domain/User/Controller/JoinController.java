package com.ex.sas.Domain.User.Controller;

import com.ex.sas.Domain.User.DTO.JoinDTO;
import com.ex.sas.Domain.User.Service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class JoinController {

    private final JoinService joinService;

    //회원가입 요청
    @PostMapping("/join")
    public String joinProcess( JoinDTO joinDTO) {
        joinService.joinProcess(joinDTO);
        return "OK";
    }
}
