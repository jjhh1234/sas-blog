package com.ex.sas.Domain.Post.Entity;

import com.ex.sas.Domain.User.Entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Table(name="post")
@Entity
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postPk;

    @ManyToOne
    @JoinColumn(name="user_pk")
    private UserEntity user;

    @Column
    private String post_title;

    @Column
    private String post_content;

    @Column
    private int hit;

    @Column
    private LocalDate post_date;
}
