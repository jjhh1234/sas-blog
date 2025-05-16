package com.ex.sas.Domain.Comment.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class CommentDTO {
    String comment_detail;

    // 조회
    String userName;

    //댓글 pk
    Integer commentId;

    //본인여부
    boolean isOwner;

    //댓글 날짜
    LocalDate comment_date;

    //댓글 좋아요 갯수
    Integer commentLikeNum;

    //댓글 목록 reponse
    //이미 DTO리스트로 반환하기 때문에 이런필드는 필요없다
    //List<CommentDTO> commentList;

}
