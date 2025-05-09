package com.ex.sas.Domain.CommentLike.Repository;

import com.ex.sas.Domain.Comment.Entity.CommentEntity;
import com.ex.sas.Domain.CommentLike.DTO.CommentLikeDTO;
import com.ex.sas.Domain.CommentLike.Entity.CommentLikeEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Integer> {
    //해당 postId를 가진 comment객체의 좋아요 행 리스트 찾아오기
    List<CommentLikeEntity> findByComment_Post_PostPk(Integer postPk);

    //해당 comment 객체를 가진 좋아요 행의 갯수 가져오기
    Integer countByComment_CommentPk(Integer commentPk);

    //해당 CommentId를 가진 댓글 좋아요 행 찾기
    CommentLikeEntity findByComment_CommentPk(Integer commentPk);

    //해당 CommentId에 달린 댓글 좋아요 리스트 가쳐오기
   List<CommentLikeEntity> findAllByComment_CommentPk(Integer commentId);

    //해당 commentId와 userId를 가진 좋아요 행 찾아오기
    CommentLikeEntity findByComment_CommentPkAndUser_UserPk(Integer commentPk, Integer userPk);

    //해당 commentId 와 userId를 가진 좋아요 행이 존재하는지 확인
    boolean existsByUser_UserPkAndComment_CommentPk(Integer userPk, Integer commentPk);

    //해당 postId, 해당 userId를 가진 좋아요 행 리스트 가져오기
    List<CommentLikeEntity> findByComment_Post_PostPkAndUser_UserPk(Integer postPk, Integer userPk);

    //해당 postId에 달린 댓글Id랑 졸아요 갯수 리스트 가져오기
    @Query("SELECT p.comment.commentPk, COUNT(p) FROM CommentLikeEntity p WHERE p.comment.post.postPk = :postId GROUP BY p.comment.commentPk")
    List<Object[]> getCommentLikeCount(@Param("postId") Integer postId);

}
