import styled from "styled-components"
import React from "react"
import { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import { Header } from "../Component"
import axios from "axios"

const Box = styled.div`
    width: 100vw;
    height: 100vh;  
    display: flex;
    flex-direction: column;
    align-items: center;
` 
const WriteBox = styled.div`
    display: flex;
    flex-direction: column;
    padding: 30px;
    width: 800px;
    background-color: lightGrey;
     margin-top: 30px;
    
`
const P = styled.p`
    font-weight: bold;
    font-size: 20px;
    margin:0;
`

const Title = styled.textarea`
    margin-bottom: 30px;
     font-size: 15px;
`

const Content = styled.textarea` 
    height: 300px;
     font-size: 15px;
`

const RegisterBtn = styled.button`
    margin-top: 30px;
    width:300px;
    height: 50px;

`

export default function Write(){
const [title, setTitle] = useState('');
const [content, setContent] = useState('');
const navigate = useNavigate();

const titleHandler = (e : React.ChangeEvent<HTMLTextAreaElement>) => {
    setTitle(e.target.value);
}

const contentHandler = (e : React.ChangeEvent<HTMLTextAreaElement>) => {
    setContent(e.target.value);
}

const register = async() => {
    try{
        const response = await axios.post(`http://localhost:8080/post`,
            {
                title : title,
                content : content
            },
            {
               headers: {
                   Authorization: 'Bearer ' + localStorage.getItem('JWT')
               }
           });
           alert("게시가 완료되었습니다.");
           navigate("/Main");
   }catch(e){
       console.error(e);
       alert("게시에 실패했습니다.");
   }
}

    return(
        <Box>
            <Header userLogin = {true}/>
            <WriteBox>
                <P>제목</P>
                <Title onChange={(e) => {titleHandler(e)}} />
                <P>내용</P>
                <Content onChange={(e) => {contentHandler(e)}}/>
            </ WriteBox>
            <RegisterBtn onClick={()=>{register()}}>게시하기</ RegisterBtn>
        </ Box>
    )
}