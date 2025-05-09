package com.ex.sas.Domain.PostLike.Entity;

import com.ex.sas.Domain.Post.Entity.PostEntity;
import com.ex.sas.Domain.User.Entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="post_like")
public class PostLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postLikePk;

    @ManyToOne
    @JoinColumn(name = "user_pk")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "post_pk")
    private PostEntity post;
}
