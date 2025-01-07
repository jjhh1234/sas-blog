package com.ex.sas.Domain.Post.Repository;

import com.ex.sas.Domain.Post.Entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity,Integer> {
}
