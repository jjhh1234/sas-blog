package com.ex.sas.Domain.PostLike.Repository;

import com.ex.sas.Domain.PostLike.Entity.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Integer> {
}
