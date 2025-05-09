package com.ex.sas.Domain.Post.Repository;

import com.ex.sas.Domain.Comment.Entity.CommentEntity;
import com.ex.sas.Domain.Post.Entity.PostEntity;
import com.ex.sas.Domain.User.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity,Integer> {
    //post 테이블에 해당 userid 와 postid를 가진 행이 존재하는가
    boolean existsByUser_UserPkAndPostPk (Integer userId, Integer postId);


}
