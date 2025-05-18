package com.ex.sas.Domain.Comment.Repository;

import com.ex.sas.Domain.Comment.Entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    //post id 에 따른 댓글 목록 불러오기
    List<CommentEntity> findByPost_PostPk(Integer postId);

}
