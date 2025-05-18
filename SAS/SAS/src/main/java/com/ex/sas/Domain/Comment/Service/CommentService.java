package com.ex.sas.Domain.Comment.Service;

import com.ex.sas.Domain.Comment.DTO.CommentDTO;
import com.ex.sas.Domain.Comment.Entity.CommentEntity;
import com.ex.sas.Domain.Comment.Repository.CommentRepository;
import com.ex.sas.Domain.CommentLike.Entity.CommentLikeEntity;
import com.ex.sas.Domain.CommentLike.Repository.CommentLikeRepository;
import com.ex.sas.Domain.Post.Entity.PostEntity;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentLikeRepository commentLikeRepository;

    //JWT로 해당 유저 불러오기
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserEntity user = userDetails.getUserEntity();
        //영속성 컨텍스트 위반 문제로 DB에서 한번더 조회 필요

        UserEntity user2 =  userRepository.findByUserId(user.getUserId());
        return user2;
    }

    //댓글 추가 로직
    public void createCommentService(Integer postId, CommentDTO request) {
        CommentEntity commentEntity = new CommentEntity();
        //postid로 해당 글 불러오기
        PostEntity postEntity = postRepository.findById(postId)
                        .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        commentEntity.setPost(postEntity);

        //JWT 에서 유저 정보 가져와 설정
        commentEntity.setUser(getCurrentUser());

        //요청 댓글 내용 매핑
        commentEntity.setComment_detail(request.getComment_detail());
        commentEntity.setComment_date(LocalDate.now());

        commentRepository.save(commentEntity);
    }

    //글에 대한 댓글 리스트 조회 (Post에 댓글을 함께 조회하는 로직이 없기 때문에 여기서 조회 컨트롤러 필요)
    public List<CommentDTO> getCommentList(Integer postId) {
        //해당 postid를 가지는 모든 댓글 가지고오기
        List<CommentEntity> comment = commentRepository.findByPost_PostPk(postId);
        //JWT요청 유저 정보 가져오기
        UserEntity user = getCurrentUser();

        //comment를 DTO 타입으로 변환
        List<CommentDTO> commentDTOS = comment.stream()
                .map(comments -> CommentDTO.builder()
                        .comment_detail(comments.getComment_detail())
                        .userName(comments.getUser().getUser_name())
                        .commentId(comments.getCommentPk())
                        .comment_date(comments.getComment_date())
                        .isOwner(comments.getUser().equals(user))
                        .build())
                .collect(Collectors.toList());

        return commentDTOS;
    }

    //댓글 수정 로직
    public void putCommentService(Integer commentId, CommentDTO request) {
        //해당 댓글 정보 가져오기
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

            //댓글 정보 변경(날짜, 내용)
            comment.setComment_detail(request.getComment_detail());
            comment.setComment_date(LocalDate.now());

        commentRepository.save(comment);
    }

    //댓글 삭제 로직
    @Transactional
    public void deleteCommentService(Integer commentId) {

        //해당 CommentI에 달린 좋아요 리스트 가져오기
        List<CommentLikeEntity> commentLikeList = commentLikeRepository.findAllByComment_CommentPk(commentId);
        if(commentLikeList != null) {
            commentLikeRepository.deleteAll(commentLikeList);
        }

        commentRepository.deleteById(commentId);
    }
}
