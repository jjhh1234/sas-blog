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
@Table(name="Post")
@Entity
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer post_pk;

    @ManyToOne
    @JoinColumn(name="user_pk")
    private UserEntity user;

    @Column
    private String post_name;

    @Column
    private String post_detail;

    @Column
    private Integer emotion_status;

    @Column
    private String post_imageURL;

    @Column
    private Integer hit;

    @Column
    private LocalDate post_date;
}
