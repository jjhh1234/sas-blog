package com.ex.sas.Domain.Post.Controller;

import com.ex.sas.Domain.Post.DTO.PostDTO;
import com.ex.sas.Domain.Post.Service.PostService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    //글 추가
    @PostMapping("/post")
    public ResponseEntity<PostDTO> postContent(@RequestBody PostDTO postDTO) {
        postService.createPost(postDTO);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //글 조회
    @GetMapping("/post/{post_pk}")
    public ResponseEntity<PostDTO> getContent(@PathVariable Integer post_pk){
        PostDTO response = postService.gettingPost(post_pk);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //글 조회수 추가
    @PostMapping("/postHit/{post_pk}")
    public ResponseEntity<PostDTO> postHit(@PathVariable Integer post_pk){
        postService.postHit(post_pk);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //글 페이징 목록 조회 테스트
    @GetMapping("/pageList")
    public Page<PostDTO> list(@RequestParam(value = "page", defaultValue = "0") Integer page){
       return postService.pageList(page);
    }

    //글 수정
    @PutMapping("/post/{post_pk}")
    public ResponseEntity<PostDTO> putContent(@PathVariable Integer post_pk, @RequestBody PostDTO postDTO) {
        postService.updatePost(post_pk, postDTO);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //글 삭제
    @DeleteMapping("/post/{post_pk}")
    public ResponseEntity<PostDTO> deleteContent(@PathVariable Integer post_pk) {
        postService.deletePost(post_pk);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
