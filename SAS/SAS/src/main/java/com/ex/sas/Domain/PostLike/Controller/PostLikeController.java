package com.ex.sas.Domain.PostLike.Controller;

import com.ex.sas.Domain.PostLike.DTO.PostLikeDTO;
import com.ex.sas.Domain.PostLike.Service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostLikeController {
    private final PostLikeService postLikeService;

    //좋아요 추가
    @PostMapping("/postLike/{post_pk}")
    public ResponseEntity<PostLikeDTO> createPostLike(@PathVariable("post_pk") Integer post_pk) {
        postLikeService.postLikeService(post_pk);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //좋아요 갯수 조회
    @GetMapping("/postLike/{post_pk}")
    public ResponseEntity<PostLikeDTO> getPostLike(@PathVariable("post_pk") Integer post_pk) {
        PostLikeDTO postLikeDTO = postLikeService.getPostLikeNum(post_pk);
        return ResponseEntity.status(HttpStatus.OK).body(postLikeDTO);
    }

    //좋아요 여부 조회 (중복 여부 확인 및 좋아요 색상 변경 가능)
    @GetMapping("/postLikeSet/{post_pk}")
    public ResponseEntity<PostLikeDTO> getPostLikeSet(@PathVariable("post_pk") Integer post_pk) {
        PostLikeDTO postLikeDTO = postLikeService.getPostLikeBool(post_pk);
        return ResponseEntity.status(HttpStatus.OK).body(postLikeDTO);
    }

    // 좋아요 취소
    @DeleteMapping("/postLike/{post_pk}")
    public ResponseEntity<PostLikeDTO> deletePostLike(@PathVariable("post_pk") Integer post_pk) {
        postLikeService.deletePostLikeService(post_pk);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
