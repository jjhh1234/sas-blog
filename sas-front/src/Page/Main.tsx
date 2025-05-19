import styled from "styled-components";
import { Header } from "../Component";
import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import React from "react";

const Box = styled.div`
    width: 100vw;
    height: 100vh;
    display: flex;
    flex-direction: column;
    align-items : center;
    justify-content: center;
` 

const PostBox = styled.div`
    display: flex;
    flex-direction: column;
    align-items : center;
    width:90%;
    height:90%;
    margin-top: 80px;
    position: relative;
    
`
const Bar = styled.div`
    width:80%;
    height: 60px;
    background-color: green;
    display: flex;
    align-items :center;
    justify-content :space-between;
    position: absolute;
    padding-right: 5%;
    padding-left: 5%;
`
const Line = styled.div`
    width: 90%;
    height: 1px;
    background-color: green;
`

const P = styled.p`
    font-weight: bold;
    font-size: 18px;
    color: blue;
    z-index:1;
    color: white;
`
const Num = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;
`

const Title = styled.div`
     display: flex;
    flex-direction: column;
     align-items: center;
     cursor: pointer;
`

const User = styled.div`
     display: flex;
    flex-direction: column;
     align-items: center;
`

const Date = styled.div`
     display: flex;
    flex-direction: column;
     align-items: center;
`

const Hit = styled.div`
     display: flex;
    flex-direction: column;
     align-items: center;
`

const T = styled.p`
    font-size: 18px;
   
`
const Post = styled.div`
     width: 80%;
     display: grid;
    grid-template-columns: 1fr 2fr 1fr 1fr 1fr;
    gap: 20px;  
`

const Tit = styled.p`
color: blue;
  text-decoration: underline;
    font-size: 18px;
`

const NumberBox = styled.div`
  width: 100px;
  height: 40px;
  display: flex;
    margin-bottom: 40px;
`

const Number = styled.button`
`


type PostType = {
    postId: number;
    title: string;
    userName: string;
    post_date: string;
    owner: boolean;
    hit: number;
  };

export default function Main(){
const [post, setPost] = useState<PostType[]>([]);
const [totalPages, setTotalPages] = useState(0);
const [pageNumber, setPageNumber] = useState(0);
const navigate = useNavigate();


useEffect(() => {
    const fetchData = async() => {
        try{
            const response = await axios.get(`/pageList?page=${pageNumber}`,
                {
                    headers: {
                        Authorization: 'Bearer ' + localStorage.getItem('JWT')
                    }
                }
            );
            setPost(response.data.content);
            setTotalPages(response.data.totalPages);
        }catch(e){
            console.error(e);
            alert("데이터를 불러오지 못했습니다.")
        }
    }
    fetchData();

},[pageNumber]);

const titleButton = ( postId : number) => {
    navigate('/Post', {state: {postId}});
    
}

const pageChange = (page : number ) => {
    setPageNumber(page);
}
    return(
        <Box>
        <Header userLogin = {true}/>
        <PostBox>
  <Bar />
  <Post>
    <Num><P>글번호</P></Num>
    <Title><P>제목</P></Title>
    <User><P>글쓴이</P></User>
    <Date><P>작성날짜</P></Date>
    <Hit><P>조회</P></Hit>
  </Post>

  {/* 데이터 줄은 map으로 */}
  {post.map((post) => (
    <React.Fragment>
    <Post key={post.postId}>
      <Num><T>{post.postId}</T></Num>
      <Title><Tit onClick={() => titleButton(post.postId)}>{post.title}</Tit></Title>
      <User><T>{post.userName}</T></User>
      <Date><T>{post.post_date}</T></Date>
      <Hit><T>{post.hit}</T></Hit>
    </Post>
    <Line />
    </ React.Fragment>
  ))}
</PostBox>
<NumberBox>
    {Array.from({ length: totalPages} , (_,i) => (
        <Number onClick={() => pageChange(i)} key={i}>{i+1}</Number>
    ))}
</ NumberBox>
        </ Box>
    )
}