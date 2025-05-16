package com.ex.sas.Error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //사용자
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT-001", "사용자를 찾을 수 없습니다."),

    //post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "POST-001", "게시물을 찾을 수 없습니다."),

    //comment
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "COMMENT-001", "댓글을 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;


}
