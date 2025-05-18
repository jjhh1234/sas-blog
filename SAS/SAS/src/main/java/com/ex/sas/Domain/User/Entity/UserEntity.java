package com.ex.sas.Domain.User.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userPk;

    //유저 닉네임
    @Column
    private String user_name;

    @Column
    private String user_introduce;

    //유저 아이디
    @Column
    private String userId;

    @Column
    private String password;

}
