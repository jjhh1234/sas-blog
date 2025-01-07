package com.ex.sas.Domain.CommentLike.Repository;

import com.ex.sas.Domain.CommentLike.Entity.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Integer> {
}
