package com.ex.sas.Domain.CommentLike.Controller;

import com.ex.sas.Domain.CommentLike.DTO.CommentLikeDTO;
import com.ex.sas.Domain.CommentLike.Service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    //댓글 좋아요
    @PostMapping("/commentLike/{commentId}")
    public ResponseEntity<CommentLikeDTO> postCommentLike(@PathVariable Integer commentId) {
        commentLikeService.postCommentLikeService(commentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //좋아요 갯수 조회 (댓글 별)
    @GetMapping("/commentLike/{commentId}")
    public ResponseEntity<CommentLikeDTO> getCommentLike(@PathVariable Integer commentId) {
        CommentLikeDTO response = commentLikeService.getCommentLikeNum(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //해당 postId의 댓글 Id 및 Id별 좋아요 갯수 리스트 반환
    @GetMapping("/commentLikeNumList/{postId}")
    public ResponseEntity<List<CommentLikeDTO>> getCommentLikeNumList(@PathVariable Integer postId) {
       List<CommentLikeDTO> response = commentLikeService.getCommentLikeNumList(postId);
       return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //댓글 좋아요 여부 확인(보류, 필요없을듯)
    @GetMapping("/commentLikeSet/{commentId}")
    public ResponseEntity<CommentLikeDTO> getCommentLikeSet(@PathVariable Integer commentId) {
        CommentLikeDTO response = commentLikeService.getCommentLikeSetService(commentId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    //해당 게시글, 유저가 좋아요 누른 행의 Id 반환
    @GetMapping("/commentLikeList/{postId}")
    public ResponseEntity<List<CommentLikeDTO>> getCommentLikeList(@PathVariable Integer postId) {
        List<CommentLikeDTO> commentIdList = commentLikeService.getCommentLikeList(postId);

        return ResponseEntity.status(HttpStatus.OK).body(commentIdList);
    }

    //댓글 좋아요 취소
    @DeleteMapping("/commentLike/{commentId}")
    public ResponseEntity<CommentLikeDTO> deleteCommentLike(@PathVariable Integer commentId) {
        commentLikeService.deleteCommentLikeService(commentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
