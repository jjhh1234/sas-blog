import styled from "styled-components"
import { Header } from "../Component"
import { useState, useEffect } from "react"
import { useNavigate } from "react-router-dom"
import axios from "axios"

//div
const Div = styled.div`
    width:30%;
    height: 70%;
`
const LoginBox = styled.div`
    display: flex;
    justify-content:center;
    align-items: center;
    width: 100vw;
    height: 100vh;
`
const ButtonBox = styled.div`
    display: flex;
    justify-content: space-between;
`

const Box = styled.div`
    width: 100vw;
    height: 100vh;
    overflow: hidden;
` 
//

const T = styled.p`
    font-weight: bold;
    font-size: 23px;
    color: green;
`

const P = styled.p`
    font-weight: bold;
    font-size: 18px;
    color: green;
    margin: 5px 0px 10px 0px;
    
`
const IdBox = styled.input`
    box-sizing: border-box;
    font-size: 25px;
    padding: 5px;
    width: 100%;
    height: 40px;
    border-color: lightgray;
    border-width: 1px;
`
const PasswordBox = styled.input`
    box-sizing: border-box;
    font-size: 25px;
    padding: 5px;
    width: 100%;
    margin-bottom: 10px;
    height: 40px;
    border-color: lightgray;
    border-width: 1px;
`

const NickName = styled.input`
    box-sizing: border-box;
    font-size: 25px;
    padding: 5px;
    width: 100%;
    margin-bottom: 10px;
    height: 40px;
    border-color: lightgray;
    border-width: 1px;
`;

const Intro = styled.input`
    box-sizing: border-box;
    font-size: 25px;
    padding: 5px;
    width: 100%;
    margin-bottom: 10px;
    height: 40px;
    border-color: lightgray;
    border-width: 1px;
`;

const SignUpButton = styled.button`
 font-weight: bold;
    font-size: 18px;
    color: white;
    background-color: green;
    border-color: green;
    width: 100%;
    height: 50px;
    border-width: 2px;
    cursor: pointer;
    overflow: hidden;
`

export default function Login() {
    const [id , setId] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [intro, setIntro] = useState('');
    const [button, setButton] = useState(false);
    const navigate = useNavigate();

    const idSet = (event: React.ChangeEvent<HTMLInputElement>) => {
        setId(event.target.value.trim())
    }

    const passwordSet = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(event.target.value.trim())
    }

    const nameSet = (event: React.ChangeEvent<HTMLInputElement>) => {
        setName(event.target.value.trim())  
    }

    const introSet = (event: React.ChangeEvent<HTMLInputElement>) => {
        setIntro(event.target.value.trim())
    }

    const SignUpHandler = () => {
        if(!id || !password || !name || !intro){
            alert("모든 내용을 입력해주세요.");
            return;
        }
        setButton(true);
    }

    useEffect(() => {
        if(button==true){
            (async () => {
                try{
                    await axios.post(`/join`,{
                        userId : id,
                        userName: name,
                        userIntroduce: intro,
                        userPassword: password
                    });
                    alert("회원가입이 완료되었습니다.");
                    navigate("/Login");
                }
                catch(e){
                    console.error(e);
                }
                finally{
                    setButton(false);
                }
            })();
        }
    },[button])
    
    return(
        <Box>
        <Header userLogin={false} />
        <LoginBox>
            <Div>
                <T>회원가입</T>
                <P>로그인 아이디</P>
                <IdBox type="text" placeholder="아이디" value={id} onChange={idSet}></ IdBox>
                <P>로그인 비밀번호 </P>
                <PasswordBox type="text" placeholder="비밀번호" value={password} onChange={passwordSet}></ PasswordBox>
                <P>닉네임</ P>
                <NickName type="text" placeholder="닉네임" value={name} onChange={nameSet}></ NickName>
                <P>자기소개</ P>
                <Intro type="text" placeholder="한줄소개" value={intro} onChange={introSet}></ Intro>
                <ButtonBox>
                    <SignUpButton onClick={SignUpHandler}>회원가입</ SignUpButton>  
                </ ButtonBox>
            </ Div>
        </ LoginBox>     
        </ Box>
    )
}