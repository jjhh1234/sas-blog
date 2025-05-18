package com.ex.sas.Domain.PostLike.Repository;

import com.ex.sas.Domain.PostLike.Entity.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Integer> {

    //쿼리로 요청 postId 를 가진 게시글의 좋아요 테이블 행 갯수 세기
    @Query("SELECT COUNT(p) FROM  PostLikeEntity p WHERE p.post.postPk = :postId")
    Integer findByPost(@Param("postId") Integer post);

    //postId와 userId를 가지는 PostLikeEntity 객체 찾기
    PostLikeEntity findByUser_UserPkAndPost_PostPk(Integer userId, Integer postId);

    //userId 와 post Id를 가진 행이 있으면 true를 반환
    boolean existsByUser_UserPkAndPost_PostPk(Integer userId, Integer postId);

    //해당 postId를 가지는 좋아요행 리스트 찾아오기
    List<PostLikeEntity> findByPost_PostPk(Integer postId);

}
