package com.ex.sas.Domain.CommentLike.DTO;

import lombok.*;
import org.checkerframework.checker.units.qual.N;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CommentLikeDTO {
    Integer commentId;

    Integer commentLikeNum;

    boolean isOwner;

    public CommentLikeDTO(Integer commentId) {
        this.commentId = commentId;
    }

    public CommentLikeDTO(boolean answer){
        this.isOwner = answer;
    }

    public CommentLikeDTO(Integer commentId, Integer commentLikeNum) {
        this.commentId = commentId;
        this.commentLikeNum = commentLikeNum;
    }
}
