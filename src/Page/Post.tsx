import styled from "styled-components";
import { useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { Header } from "../Component";
import axios from "axios";
import React from "react";

const Box = styled.div`
    width: 100vw;
    height: 100vh;  
    display: flex;
    flex-direction: column;
    align-items: center;
` 
const PostBox = styled.div`
    display: flex;
    flex-direction: column;
    padding: 30px;
    width: 800px;
`

const PostHeader = styled.div`
    display: flex;
    justify-content: space-between;
    align-items: center;
`

const BottomBox = styled.div`
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: flex-end;
    margin-top: 10px;
`

const NameBox = styled.div`
    display: flex;
    flex-direction: column;
`

const ButtonBox = styled.div`
    display: flex;
    justify-content :space-between;
    height: 40px;
    width: 220px;
`

const Title = styled.p`
    font-weight: bold;
    font-size: 25px;
    margin:0;
`

const IntPutTitle = styled.input`
    font-weight: bold;
    font-size: 25px;
    margin:0;
`

const UserName = styled.p`
    font-weight: bold;
`

const PutButton = styled.button`
    width: 100px;
`

const DeleteButton = styled.button`
    width: 100px;
`

const Write = styled.div`
`

const InputWrite = styled.input`
`

const Date = styled.div`
    margin-right: 10px;
`

const Heart = styled.div`
    margin-right: 5px;  
    cursor: pointer;
     color: red;
`

const HeartNum = styled.p`
    margin:0;
   
`
const Line = styled.div`
    height: 1px;
     width: 800px;
    background-color: grey;
    margin-top: 15px;
`
const CommentBox = styled.div`
    display: flex;
    flex-direction: column;
   padding: 20px;
    background-color: lightgrey;
     width: 800px;
`
const CommentHeader = styled.div`
    display: flex;
    align-items: center;
    justify-content: space-between;
`

const CommentButtonBox = styled.div`
    display: flex;
    justify-content :space-between;
    height: 40px;
    width: 220px;
`

const CommentWriteBox = styled.div`
    display: flex;
    flex-direction: row;
   padding: 20px;
    background-color: lightGrey;
     width: 800px;
     height: 50px;
     margin-top: 10px;
`

const CommentEditBox = styled.input`
`

const WriteBox = styled.textarea`
     width: 100%;
     margin-right: 15px;
`
const RegistBtn = styled.button`
    width: 100px;
    hight: 50px;
`

type CommentList = {
    commentId : number;
    userName: string;
    comment_detail: string;
    comment_date: string;
    owner : boolean;
}

type CommentLikeNum = {
    commentId: number;
    commentLikeNum : number;

}

export default function Post(){
    const location = useLocation();
    const postId : number = location.state.postId;
    const navigate = useNavigate();
    const [title, setTitle] = useState('');
    const [userName, setUserName] = useState('');
    const [content, setContent] = useState('');
    const [postDate, setPostDate] = useState('');
    const [postOwner, setPostOwner] = useState(false);
    const [postLikeNum, setPostLikeNum] = useState(0);
    const [commentList, setCommentList] = useState<CommentList[]>([]);
    const [postLikeSet, setPostLikeSet] = useState(false);
    const [newCommentId, setNewCommentId] = useState<number[]>([]);
    const [commentLikeNum, setCommentLikeNum] = useState<CommentLikeNum[]>([]); 
    const [likeChange, setLikeChange] = useState(false);
    const [comment, setComment] = useState('');
    const [postEditMode, setPostEditMode] = useState(false);
    const [commentEditMode, setCommentEditMode] = useState(false);
    const [commentId, setCommentId] = useState(0);
    const [commentContent, setCommentContent] = useState('');
    const [editingCommentId, setEditingCommentId] = useState(0);

    //게시물 내용 불러오기
    useEffect(() => {
        const fetchData = async() => {
        try{
            const response = await axios.get(`http://localhost:8080/post/${postId}`, {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('JWT')
                }
            });
            setTitle(response.data.title);
            setUserName(response.data.userName);
            setContent(response.data.content);
            setPostDate(response.data.post_date);
            setPostOwner(response.data.owner);
            }
        catch(e){
            console.error(e);
        }
    };
        fetchData();
    },[postEditMode]);

    //글 조회수 추가
     useEffect(() => {
        const fetchData = async() => {
        try{
            const response = await axios.post(`http://localhost:8080/postHit/${postId}`,{}, {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('JWT')
                }
            });    
        }
        catch(e){
            console.error(e);
        }
    };
        fetchData();
    },[]);
    
    //게시글 좋아요 갯수 불러오기
    useEffect(()=> {
        const fetchData = async() => {
            try{
                const response = await axios.get(`http://localhost:8080/postLike/${postId}`,{
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('JWT')
                    }
                }); 
                setPostLikeNum(response.data.postlikeNum); 
            }
            catch(e){
                console.error(e);
            }
        }
        fetchData();
       
    },[postLikeSet]);

    //게시글 좋아요 여부 불러오기 (이게 false 이면 눌렀을때 좋아요 취소)
    useEffect(()=> {
        const fetchData = async() => {
            try{
            const response = await axios.get(`http://localhost:8080/postLikeSet/${postId}`,{
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('JWT')
                }
            });
            setPostLikeSet(response.data.owner);
        }
            catch(e){
                console.error(e);
            }
            }
        fetchData();
    },[]); 

    //게시글 좋아요(postLikeSet이 true 이면 좋아요 취소 api, false이면 좋아요 추가 api)
    const handlePostLike = async() => {
            //true이면 좋아요 취소
            if(postLikeSet){
                try{
                    const response = await axios.delete(`http://localhost:8080/postLike/${postId}`,{
                        headers: {
                            Authorization: 'Bearer ' + localStorage.getItem('JWT')
                        }
                    });
                    setPostLikeSet(false);
                }catch(e){
                    console.log(e);
                }
                // false 이면 좋아요 추가
            }else{
                try{
                    const response = await axios.post(`http://localhost:8080/postLike/${postId}`,{},{
                        headers: {
                            Authorization: 'Bearer ' + localStorage.getItem('JWT')
                        }
                    });
                    setPostLikeSet(true);
                }catch(e){
                    console.error(e);
                }
            }
        };
        
    //댓글 Id 및 좋아요 갯수 불러오기
    useEffect(() => {
        const fetchData = async() => {
            try{
                 const response = await axios.get(`http://localhost:8080/commentLikeNumList/${postId}`,{
                        headers: {
                            Authorization: 'Bearer ' + localStorage.getItem('JWT')
                        }
                    });
                    setCommentLikeNum(response.data);
            }catch(e){
                console.error(e);
            }
        }
        fetchData();
    },[likeChange])
 
    //내가 좋아요 누른 댓글 ID 리스트 가져오기 
    useEffect(() => {
        const fetchData = async() => {
            try{
                const response = await axios.get<CommentList[]>(`http://localhost:8080/commentLikeList/${postId}`,{
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('JWT')
                    }
                });
                setNewCommentId(response.data.map(item => item.commentId));
               
            }catch(e){
                console.error(e);
            }
        };
        fetchData();
    },[likeChange])

    //댓글 불러오기
        const fetchData = async() => {
            try{
                const response = await axios.get(`http://localhost:8080/commentList/${postId}`,{
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('JWT')
                    }
                });
              
                setCommentList(response.data);
            }catch(e){
                console.error(e);
            }
        };
    

    useEffect(() => {
        fetchData();
    },[commentEditMode]);

    //좋아요 버튼 핸들러
    const commentIdHandler = async( commentId: number ) => {
        const response = await axios.get(`http://localhost:8080/commentLikeSet/${commentId}`,{
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('JWT')
            }
        });
        let isLike = response.data.owner;
    if(!isLike){
        //owner가 false 이면 좋아요 추가
    try{
        const response = await axios.post(`http://localhost:8080/commentLike/${commentId}`,{},{
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('JWT')
            }
        });
    }catch(e){
        console.error(e);
    }
    }else
    {
        //owner가 true 이면 좋아요 취소
        try{
            const response = await axios.delete(`http://localhost:8080/commentLike/${commentId}`,{
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('JWT')
                }
            });
        }catch(e){
            console.error(e);
        }}
    
        if(!likeChange){
            setLikeChange(true);
        }else{
            setLikeChange(false);
        }
    }
                    
    //댓글 박스 입력 핸들러
    const commentRegister = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
        setComment(e.target.value);
    }    

    //등록 버튼 핸들러
    const registerBtn = async() => {
        try{
            const response = await axios.post(`http://localhost:8080/comment/${postId}`,
                {
                    comment_detail : comment
                }
                ,{
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('JWT')
                }
            });   
            fetchData();
            setComment("");
        }catch(e){
            console.error(e);
        }   
    }

    //댓글 삭제 핸들러
    const commentDeleteHandler = async(commentId : number) => {
        try{
            const response = await axios.delete(`http://localhost:8080/comment/${commentId}`,
                {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('JWT')
                }
            });  
            fetchData(); 
            alert("삭제가 완료되었습니다.");
        }catch(e){
            console.error(e);
        }   
    }

    //게시글 삭제 핸들러
    const postDeleteHandler = async() => { 
        const confirmed = window.confirm("게시글을 삭제하시겠습니까?");
        if(confirmed){
            try{
                const response = await axios.delete(`http://localhost:8080/post/${postId}`,
                    {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('JWT')
                    }
                });  
                navigate('/Main');
                alert("삭제가 완료되었습니다.");
            }catch(e){
                console.error(e);
            }   
        }
    };

    //게시글 수정모드로 변경 버튼이벤트
    const EditPost = () => {
        setPostEditMode(true);
    }

    // 게시글 수정모드 취소 버튼이벤트
    const notEditPost = () => {
        setPostEditMode(false);
    }

    //게시글 수정핸들러
        const editPostHandler = async() => {
                try{
                    const response = await axios.put(`http://localhost:8080/post/${postId}`,
                        {
                            title : title,
                            content : content
                        }
                        ,
                        {
                        headers: {
                            Authorization: 'Bearer ' + localStorage.getItem('JWT')
                        }
                    });  
                    alert("수정이 완료되었습니다.");
                    setPostEditMode(false);
                }catch(e){
                    console.error(e);
                }   
        };
        
    //댓글 수정모드로 변경 버튼이벤트
    const EditComment = (commentId :number, commentDetail : string ) => {
        setEditingCommentId(commentId);
        setCommentId(commentId);
        setCommentContent(commentDetail);
        setCommentEditMode(true);
    }

    //댓글 수정모드 취소 
    const notEditComment = () => {
        setCommentEditMode(false);
    }

    //댓글 수정핸들러
    const editCommentHandler = async() => {
        try{
            const response = await axios.put(`http://localhost:8080/comment/${commentId}`,
                {
                    comment_detail : commentContent
                }
                ,
                {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('JWT')
                }
            });  
            alert("수정이 완료되었습니다.");
            setCommentEditMode(false);
        }catch(e){
            console.error(e);
        }   
    };


    return(
        <Box>
            <Header userLogin = {true} />
            {postEditMode ?(
                <PostBox>
                <PostHeader>
                    <NameBox>
                        <IntPutTitle value={title}
                        onChange={(e) => setTitle(e.target.value)} />
                        <UserName>{userName}</ UserName>
                    </NameBox>
                    <ButtonBox style={{ visibility: postOwner ? 'visible' : 'hidden' }}>
                        <PutButton onClick={() => editPostHandler()}>수정완료</PutButton>
                        <DeleteButton onClick={()=> notEditPost()}>수정취소</DeleteButton>
                    </ButtonBox>
                </ PostHeader>
                <InputWrite value={content}
                onChange={(e) => setContent(e.target.value)} />
                    <BottomBox>
                        <Date>{postDate}</Date>
                        <Heart style={{color : postLikeSet ? 'blue' : 'red'}} onClick={handlePostLike} >좋아요</Heart>
                        <HeartNum>{postLikeNum}</ HeartNum>
                    </BottomBox>
            </ PostBox>
            ) : (
                <PostBox>
                <PostHeader>
                    <NameBox>
                        <Title>{title}</ Title>
                        <UserName>{userName}</ UserName>
                    </NameBox>
                    <ButtonBox style={{ visibility: postOwner ? 'visible' : 'hidden' }}>
                        <PutButton onClick={() => EditPost()}>수정</PutButton>
                        <DeleteButton onClick={()=> postDeleteHandler()}>삭제</DeleteButton>
                    </ButtonBox>
                </ PostHeader>
                <Write>{content}</ Write>
                    <BottomBox>
                        <Date>{postDate}</Date>
                        <Heart style={{color : postLikeSet ? 'blue' : 'red'}} onClick={handlePostLike} >좋아요</Heart>
                        <HeartNum>{postLikeNum}</ HeartNum>
                    </BottomBox>
            </ PostBox>
            )}
            
            {commentList.map((comment) => (
                <CommentBox key={comment.commentId}>
                    {commentEditMode && comment.commentId === editingCommentId ? (
                        <React.Fragment>
                        <CommentHeader>
                            <UserName>{comment.userName}</ UserName>
                            <CommentButtonBox style={{visibility : comment.owner ? 'visible' : 'hidden'}}>
                                <PutButton onClick={() => {editCommentHandler()}}>수정완료</PutButton>
                                <DeleteButton onClick={() => {notEditComment()}}>수정취소</DeleteButton>    
                            </ CommentButtonBox>    
                        </ CommentHeader>
                        <CommentEditBox value={commentContent}
                            onChange={(e) => setCommentContent(e.target.value)}
                        /> 
                        </ React.Fragment>
                    ) : (
                        <React.Fragment>
                        <CommentHeader>
                            <UserName>{comment.userName}</ UserName>
                            <CommentButtonBox style={{visibility : comment.owner ? 'visible' : 'hidden'}}>
                                <PutButton onClick={() => {EditComment(comment.commentId , comment.comment_detail)}}>수정</PutButton>
                                <DeleteButton onClick={() => commentDeleteHandler(comment.commentId)}>삭제</DeleteButton>    
                            </ CommentButtonBox>    
                        </ CommentHeader>
                        <Write>{comment.comment_detail}
                        </Write>
                        </ React.Fragment>
                    )}
              
                <BottomBox>
                    <Date>{comment.comment_date}</Date>
                    <Heart style={{color : newCommentId.includes(comment.commentId) ? 'blue' : 'red'}}
                    onClick={() => commentIdHandler(comment.commentId)} >좋아요</Heart>               
                        <HeartNum>
                            {
                            commentLikeNum.find(like => like.commentId === comment.commentId)?.commentLikeNum ?? 0
                            }
                        </ HeartNum>
                </BottomBox>  
                <Line />
            </ CommentBox>  
            ))}
            <CommentWriteBox>
                <WriteBox value={comment} onChange={(e) => commentRegister(e)}></ WriteBox>
                <RegistBtn onClick={registerBtn}>등록</ RegistBtn>
            </ CommentWriteBox> 
        </ Box>
    )
}
