package com.ex.sas.Domain.CommentLike.Entity;

import com.ex.sas.Domain.Comment.Entity.CommentEntity;
import com.ex.sas.Domain.User.Entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="comment_like")
public class CommentLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentLikePk;

    @ManyToOne
    @JoinColumn(name="user_pk")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name="comment_pk")
    private CommentEntity comment;
}
