package com.ex.sas.Domain.Comment.Entity;

import com.ex.sas.Domain.Post.Entity.PostEntity;
import com.ex.sas.Domain.User.Entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="Comment")
public class CommentEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer commentPk;

    @ManyToOne
    @JoinColumn(name="post_pk")
    private PostEntity post;

    @ManyToOne
    @JoinColumn(name="user_pk")
    private UserEntity user;

    @Column
    private String comment_detail;

    @Column
    private LocalDate comment_date;
}
