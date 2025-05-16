package com.ex.sas.Domain.CommentLike.Service;

import com.ex.sas.Domain.Comment.Entity.CommentEntity;
import com.ex.sas.Domain.Comment.Repository.CommentRepository;
import com.ex.sas.Domain.CommentLike.DTO.CommentLikeDTO;
import com.ex.sas.Domain.CommentLike.Entity.CommentLikeEntity;
import com.ex.sas.Domain.CommentLike.Repository.CommentLikeRepository;
import com.ex.sas.Domain.Post.Repository.PostRepository;
import com.ex.sas.Domain.User.Entity.UserEntity;
import com.ex.sas.Domain.User.Repository.UserRepository;
import com.ex.sas.Domain.User.Service.CustomUserDetails;
import com.ex.sas.Error.CustomException;
import com.ex.sas.Error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    //JWT로 해당 유저 불러오기
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserEntity user = userDetails.getUserEntity();
        //영속성 컨텍스트 위반 문제로 DB에서 한번더 조회 필요

        UserEntity user2 =  userRepository.findByUserId(user.getUserId());
        return user2;
    }

    //댓글 좋아요 추가
    public void postCommentLikeService(Integer commentId){
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        UserEntity user = getCurrentUser();

        //좋아요 중복 아닐 시 좋아요 추가
        boolean isLike = commentLikeRepository.existsByUser_UserPkAndComment_CommentPk(user.getUserPk(), comment.getCommentPk());
        if(!isLike){
            CommentLikeEntity commentLike = new CommentLikeEntity();
            commentLike.setComment(comment);
            commentLike.setUser(user);
            commentLikeRepository.save(commentLike);
        }
    }

    //댓글 좋아요 갯수 조회
    public CommentLikeDTO getCommentLikeNum(Integer commentId){
       //리포지토리에서 해당 commentId를 가지는 commentLike행 갯수찾기
        CommentLikeDTO commentLikeDTO = new CommentLikeDTO(commentLikeRepository.countByComment_CommentPk(commentId));

        return commentLikeDTO;
    }

    //해당 post 에 달린 댓글 Id및 댓글 좋아요 갯수 리스트 조회
    public List<CommentLikeDTO> getCommentLikeNumList(Integer postId){
       List<Object[]> commentLikeNum = commentLikeRepository.getCommentLikeCount(postId);

       List<CommentLikeDTO> commentLikeDTOList = commentLikeNum.stream()
               .map(comment -> CommentLikeDTO.builder()
                       .commentId((Integer) comment[0])
                       .commentLikeNum(((Number) comment[1]).intValue())
                       .build())
               .collect(Collectors.toList());

       return commentLikeDTOList;
    }

    //댓글 좋아요 여부 확인
    public CommentLikeDTO getCommentLikeSetService(Integer commentId){
        UserEntity user = getCurrentUser();
        //해당 댓글Id와 user가 댓글 좋아요 테이블에 존재하는지 확인
        boolean isLiked = commentLikeRepository.existsByUser_UserPkAndComment_CommentPk(user.getUserPk(), commentId);
        if(isLiked){
            CommentLikeDTO commentLikeDTO = new CommentLikeDTO(true);
            return commentLikeDTO;
        }else {
            CommentLikeDTO commentLikeDTO = new CommentLikeDTO(false);
            return commentLikeDTO;
        }
    }

    //해당 게시글에 내가 좋아요 누른 행 리스트 가져오기
    public List<CommentLikeDTO> getCommentLikeList(Integer postId){
        UserEntity user = getCurrentUser();
        List<CommentLikeEntity> commentList = commentLikeRepository.findByComment_Post_PostPkAndUser_UserPk(postId,user.getUserPk());

        //해당 post, 해당 user가 좋아요 누른 행 Id 리스트를 DTO에 담아 반환
        List<CommentLikeDTO> commetLikeList = commentList.stream()
                .map(comment -> CommentLikeDTO.builder()
                        .commentId(comment.getComment().getCommentPk())
                        .build())
                .collect(Collectors.toList());
        System.out.println(commetLikeList);
        return commetLikeList;
    }

    //댓글 좋아요 취소
    public void deleteCommentLikeService(Integer commentId){
        UserEntity user = getCurrentUser();
        //댓글 좋아요 테이블에서 해당 유저정보와 댓글객체Id를 좋아요 행 찾아오기
        CommentLikeEntity commentLike = commentLikeRepository.findByComment_CommentPkAndUser_UserPk(commentId, user.getUserPk());
        commentLikeRepository.delete(commentLike);
    }

}
