package com.ex.sas.Domain.PostLike.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostLikeDTO {
    //좋아요 여부
    boolean isOwner;

    //글 좋아요 갯수
    Integer postlikeNum;
}
