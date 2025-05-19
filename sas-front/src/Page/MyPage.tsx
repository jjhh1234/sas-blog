import styled from "styled-components"
import { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import { Header } from "../Component"
import axios from "axios"
import React from "react"

const Box = styled.div`
    width: 100vw;
    height: 100vh;  
    display: flex;
    flex-direction: column;
    align-items: center;
`
const UserInfo = styled.div`
    display: flex;
    flex-direction: column;
    padding: 30px;
    width: 800px;   
`

const P = styled.p`
    font-weight: bold;
    font-size: 25px;
    margin:0;
    color: darkGreen;
    margin-bottom: 20px;
`

const Tx = styled.p`
    font-weight: bold;
    font-size: 25px;
    margin:0;
    color: darkGreen;
     margin-bottom: 10px;
`

const T = styled.input`
    margin-bottom: 10px;
    width: 100%;
    height: 30px;
    box-sizing: border-box;
`

const Info = styled.div`
    background-color: lightGrey;
    padding: 20px;
`

const RegisterBtn = styled.button`
    margin-top: 10px;
    width:300px;
    height: 50px;
`
const Px = styled.p`
    font-size: 25px;
`

export default function MyPage(){

const [editMode, setEditMode] = useState(false);
const [nickName, setNickName] = useState('');
const [info, setInfo] = useState('');

useEffect(() => {
    const fetchData = async() => {
        try{
            const response = await axios.get(`/profile`,{
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('JWT')
                }
            });
           setNickName(response.data.userName);
           setInfo(response.data.userIntroduce);
        }catch(e){
            console.error(e);
        }
       
    }
    fetchData();
},[])

const putHandler = () => {
     setEditMode(true);
}

const registerProfile = async() => {
    try{
        const response = await axios.put(`/profile`
            ,{
                userName : nickName,
                userIntroduce : info
            }
            ,{
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem('JWT')
            }
        });
        setEditMode(false);
    }catch(e){
        console.error(e);
    }
}

//프로필 수정 핸들러


    return(

        <Box>
            <Header userLogin = {true} />
            {editMode ? (
                <React.Fragment>
                <UserInfo>
                    <P>내정보</P>   
                    <Info>
                        <Tx>닉네임</Tx>
                        <T value={nickName} onChange={(e) => setNickName(e.target.value)}/>    
                        <Tx>자기소개</Tx>
                        <T value={info} onChange={(e) => setInfo(e.target.value)}/> 
                    </ Info>   
                </ UserInfo>
                <RegisterBtn onClick={() => {registerProfile()}}> 수정완료 </RegisterBtn>
                </ React.Fragment>
            ) : (
                <React.Fragment>
                <UserInfo>
                    <P>내정보</P>   
                    <Info>
                        <Tx>닉네임</Tx>
                        <Px>{nickName}</Px >    
                        <Tx>자기소개</Tx>
                        <Px>{info}</Px> 
                    </ Info>   
                </ UserInfo>
                <RegisterBtn onClick={putHandler}> 수정하기 </RegisterBtn>
                </ React.Fragment>
            )}
           
    </ Box>

    )
}