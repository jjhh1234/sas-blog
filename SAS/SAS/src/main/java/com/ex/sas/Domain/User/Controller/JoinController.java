package com.ex.sas.Domain.User.Controller;

import com.ex.sas.Domain.Comment.DTO.CommentDTO;
import com.ex.sas.Domain.Post.DTO.PostDTO;
import com.ex.sas.Domain.User.DTO.JoinDTO;
import com.ex.sas.Domain.User.Service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class JoinController {

    private final JoinService joinService;

    //회원가입 요청
    @PostMapping("/join")
    public String joinProcess(@RequestBody JoinDTO joinDTO) {
        joinService.joinProcess(joinDTO);
        return "OK";
    }

    //프로필 불러오기
    @GetMapping("/profile")
    public ResponseEntity<JoinDTO> getProfile() {
        JoinDTO response = joinService.getProfile();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //프로필 수정
    @PutMapping("/profile")
    public ResponseEntity<JoinDTO> updateProfile(@RequestBody JoinDTO joinDTO) {
        joinService.putProfile(joinDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
