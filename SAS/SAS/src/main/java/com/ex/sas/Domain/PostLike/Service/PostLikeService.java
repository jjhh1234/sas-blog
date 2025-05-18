package com.ex.sas.Domain.PostLike.Service;

import com.ex.sas.Domain.Post.Entity.PostEntity;
import com.ex.sas.Domain.Post.Repository.PostRepository;
import com.ex.sas.Domain.PostLike.DTO.PostLikeDTO;
import com.ex.sas.Domain.PostLike.Entity.PostLikeEntity;
import com.ex.sas.Domain.PostLike.Repository.PostLikeRepository;
import com.ex.sas.Domain.User.Entity.UserEntity;
import com.ex.sas.Domain.User.Repository.UserRepository;
import com.ex.sas.Domain.User.Service.CustomUserDetails;
import com.ex.sas.Error.CustomException;
import com.ex.sas.Error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //JWT로 해당 유저 불러오기
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        UserEntity user = userDetails.getUserEntity();
        //영속성 컨텍스트 위반 문제로 DB에서 한번더 조회 필요

        UserEntity user2 =  userRepository.findByUserId(user.getUserId());
        return user2;
    }

    //좋아요 추가
    public void postLikeService(Integer postId){
        //포스트라이크 데이터 담을 객체
        PostLikeEntity postLikeEntity = new PostLikeEntity();

        //일단 해당 post 가져오기
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        UserEntity user = getCurrentUser();

        //좋아요 중복 아닐시 좋아요 추가
        boolean isLike = postLikeRepository.existsByUser_UserPkAndPost_PostPk(user.getUserPk(), post.getPostPk());

        if(!isLike){
            postLikeEntity.setPost(post);
            postLikeEntity.setUser(user);
            postLikeRepository.save(postLikeEntity);
        }

    }

    //글 좋아요 갯수 조회
    public PostLikeDTO getPostLikeNum(Integer postId){
        //해당 글 가져오기
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        //해당 글 좋아요 갯수 불러오기
        PostLikeDTO postLikeDTO = new PostLikeDTO();
        postLikeDTO.setPostlikeNum(postLikeRepository.findByPost(post.getPostPk()));

        return postLikeDTO;
    }

    //좋아요 여부 조회
    public PostLikeDTO getPostLikeBool (Integer postId){
        //포스트라이크 데이터 담을 객체
        PostLikeDTO postLikeDTO = new PostLikeDTO();

        //해당 유저 JWT 에서 뽑기
        //좋아요를 누른 유저 정보 JWT에서 뽑기
        UserEntity user = getCurrentUser();

       boolean isLiked = postLikeRepository.existsByUser_UserPkAndPost_PostPk(user.getUserPk(), postId);

       postLikeDTO.setOwner(isLiked);

       return postLikeDTO;
    }

    //좋아요 취소
    public void deletePostLikeService (Integer postId){
        //해당 유저 뽑기
        UserEntity user = getCurrentUser();

        //해당 글과 유저 정보를 가진 객체 불러오기
        PostLikeEntity postLikeEntity  = postLikeRepository.findByUser_UserPkAndPost_PostPk(user.getUserPk(), postId);

        //해당 행 삭제
        postLikeRepository.delete(postLikeEntity);
    }
}
