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
    private Integer user_pk;

    @Column
    private String user_name;

    @Column
    private String user_introduce;

    @Column
    private String id;

    @Column
    private String password;

}
