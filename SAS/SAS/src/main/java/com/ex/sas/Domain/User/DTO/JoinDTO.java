package com.ex.sas.Domain.User.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JoinDTO {
    private String userId;

    private String userName;

    private String userIntroduce;

    private String userPassword;
}
