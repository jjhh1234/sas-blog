package com.ex.sas.Domain.Post.Repository;


import com.ex.sas.Domain.Post.Entity.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity,Integer> {
    //post 테이블에 해당 userid 와 postid를 가진 행이 존재하는가
    boolean existsByUser_UserPkAndPostPk (Integer userId, Integer postId);

    //페이징 구현을 위한 메소드
    Page<PostEntity> findAll(Pageable pageable);

}
