package com.ex.sas.Domain.Comment.Repository;

import com.ex.sas.Domain.Comment.Entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {
}
