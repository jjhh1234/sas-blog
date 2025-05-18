package com.ex.sas.Domain.Post.DTO;

import com.ex.sas.Domain.Post.Entity.PostEntity;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    Integer postId;

    String userName;

    String title;

    String content;

    int hit;

    boolean isOwner;

    LocalDate post_date;

    public PostDTO(PostEntity postEntity) {
        postId = postEntity.getPostPk();
        userName = postEntity.getUser().getUser_name();
        title = postEntity.getPost_title();
        content = postEntity.getPost_content();
        hit = postEntity.getHit();
        post_date = postEntity.getPost_date();
    }
}
