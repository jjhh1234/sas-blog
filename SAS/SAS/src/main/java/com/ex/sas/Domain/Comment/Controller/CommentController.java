package com.ex.sas.Domain.Comment.Controller;

import com.ex.sas.Domain.Comment.DTO.CommentDTO;

import com.ex.sas.Domain.Comment.Service.CommentService;
import com.ex.sas.Domain.Post.DTO.PostDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    //댓글 추가
    @PostMapping("/comment/{postId}")
    public ResponseEntity<CommentDTO> postComment(@PathVariable Integer postId, @RequestBody CommentDTO commentDTO) {
        commentService.createCommentService(postId, commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //글에대한 댓글 목록 조회 (좋아요갯수 까지)
    @GetMapping("/commentList/{postId}")
    public ResponseEntity<List<CommentDTO>> getComment(@PathVariable Integer postId) {
        List<CommentDTO> commentList = commentService.getCommentList(postId);
        return ResponseEntity.status(HttpStatus.OK).body(commentList);
    }

    //댓글 수정
    @PutMapping("/comment/{commentId}")
    public ResponseEntity<CommentDTO> putComment(@PathVariable Integer commentId, @RequestBody CommentDTO commentDTO) {
        commentService.putCommentService(commentId, commentDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<PostDTO> deleteComment(@PathVariable Integer commentId){
        commentService.deleteCommentService(commentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
