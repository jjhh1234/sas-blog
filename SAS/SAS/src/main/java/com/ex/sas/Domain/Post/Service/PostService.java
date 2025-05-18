package com.ex.sas.Domain.Post.Service;

import com.ex.sas.Domain.Comment.Entity.CommentEntity;
import com.ex.sas.Domain.Comment.Repository.CommentRepository;
import com.ex.sas.Domain.CommentLike.Entity.CommentLikeEntity;
import com.ex.sas.Domain.CommentLike.Repository.CommentLikeRepository;
import com.ex.sas.Domain.Post.DTO.PostDTO;
import com.ex.sas.Domain.Post.Entity.PostEntity;
import com.ex.sas.Domain.Post.Repository.PostRepository;
import com.ex.sas.Domain.PostLike.Entity.PostLikeEntity;
import com.ex.sas.Domain.PostLike.Repository.PostLikeRepository;
import com.ex.sas.Domain.User.Entity.UserEntity;
import com.ex.sas.Domain.User.Repository.UserRepository;
import com.ex.sas.Domain.User.Service.CustomUserDetails;
import com.ex.sas.Error.CustomException;
import com.ex.sas.Error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    //JWT로 해당 유저 불러오기
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
       UserEntity user = userDetails.getUserEntity();
       //영속성 컨텍스트 위반 문제로 DB에서 한번더 조회 필요

        UserEntity user2 =  userRepository.findByUserId(user.getUserId());
        return user2;
    }

    //글 추가 로직
    public void createPost(PostDTO request){
        PostEntity postEntity = new PostEntity();
        postEntity.setUser(getCurrentUser());
        postEntity.setPost_content(request.getContent());
        postEntity.setPost_title(request.getTitle());
        postEntity.setPost_date(LocalDate.now());

        postRepository.save(postEntity);
    }

    //글 조회 로직
    public PostDTO gettingPost(Integer postId){

            //포스트 엔티티에 찾아온 포스트 정보를 담기
            PostEntity post = postRepository.findById(postId)
                    .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

            //응답값을 담을 POSTDTO
            PostDTO response = new PostDTO();

            //본인인지 확인
            UserEntity user = getCurrentUser();

            boolean isOwner = postRepository.existsByUser_UserPkAndPostPk(user.getUserPk(), post.getPostPk());
            if(isOwner){
                response.setOwner(true);

                //작성자 닉네임 담기
                response.setUserName(post.getUser().getUser_name());

            }else{
                response.setOwner(false);
            }

            //글 내용
            response.setTitle(post.getPost_title());
            response.setContent(post.getPost_content());
            response.setPost_date(post.getPost_date());
            response.setHit(post.getHit());

            return response;
    }

    //글 조회수 추가 로직
    public void postHit (Integer postId){
        //포스트 엔티티에 찾아온 포스트 정보를 담기
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        post.setHit(post.getHit() + 1);
        postRepository.save(post);
    }

    //글 수정 로직
    public void updatePost(Integer postId, PostDTO request){
        PostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        // 해당 게시글에 수정된 게시 정보 담기
        post.setPost_title(request.getTitle());
        post.setPost_content(request.getContent());
        post.setPost_date(LocalDate.now());

        postRepository.save(post);
    }

    //글 삭제 로직
    @Transactional
    public void deletePost(Integer postId){
        //해당 postId를 가진 comment 객체의 좋아요 모두 삭제
        List<CommentLikeEntity> commentLikeList = commentLikeRepository.findByComment_Post_PostPk(postId);
        commentLikeRepository.deleteAll(commentLikeList);

        //글에 달린 댓글 리스트 찾아오기 및 삭제
        List<CommentEntity> commentList = commentRepository.findByPost_PostPk(postId);
        commentRepository.deleteAll(commentList);

        //해당 게시글 좋아요 리스트 삭제
        List<PostLikeEntity> postLikeList = postLikeRepository.findByPost_PostPk(postId);
        postLikeRepository.deleteAll(postLikeList);

        //게시글 좋아요 삭제
        postRepository.deleteById(postId);
    }

    //페이징 메소드
    public Page<PostDTO> pageList(Integer page){
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "postPk"));
        return postRepository.findAll(pageable).map(PostDTO::new);

       }
}
