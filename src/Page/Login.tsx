import styled from "styled-components"
import { Header } from "../Component"
import { useState, useEffect } from "react"
import axios from "axios"
import { useNavigate } from "react-router-dom"

//div
const Div = styled.div`
    width: 30%;
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
    padding: 5px;
    font-size: 25px;
    width: 100%;
    height: 40px;
    border-radius: 2%;
    border-color: lightgray;
    border-width: 1px;
`
const PasswordBox = styled.input`
    box-sizing: border-box;
    padding: 5px;
    font-size: 25px;
    width: 100%;
    margin-bottom: 10px;
    height: 40px;
    border-radius: 2%;
    border-color: lightgray;
    border-width: 1px;
`
const LoginButton = styled.button`
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
    const [id, setId] = useState("");
    const [password, setPassword] = useState("");
    const [login, setLogin] = useState(false);
    const navigate = useNavigate();
    const formData = new FormData();
    formData.append('username', id);
    formData.append('password', password);

    const idBox = (event: React.ChangeEvent<HTMLInputElement>) => {
        setId(event.target.value);
    }

    const passwordBox = (event: React.ChangeEvent<HTMLInputElement>) => {
        setPassword(event.target.value);
    }

   useEffect(()=> {
    if(login==true){
       (async() => {
        try {
            const response = await axios.post(`http://localhost:8080/login`,formData);
            const fulltoken : string = response.headers['authorization']; 
            const token : string = fulltoken.split(' ')[1];
            localStorage.setItem('JWT', token);
            if(localStorage.getItem('JWT')){
                navigate("/Main");
            }
         }
         catch(error){
             console.error(error);
             alert("로그인에 실패하였습니다.");
         }finally{
             setLogin(false);
         }
       })();
    }
   },[login])

   const Login = () => {
        if(!id || !password) {
            alert("모든 정보를 입력해주세요");
        }else{
           
            setLogin(true);
        }
   }
       
    return(
        <Box>
        <Header userLogin = {false} />
        <LoginBox>
            <Div>
                <T>로그인</T>
                <P>로그인 아이디</P>
                <IdBox placeholder="아이디" value={id} onChange={idBox}></ IdBox>
                <P>로그인 비밀번호 </P>
                <PasswordBox placeholder="비밀번호" value={password} onChange={passwordBox}></ PasswordBox>
                <ButtonBox>
                    <LoginButton onClick={Login}>로그인</ LoginButton>  
                </ ButtonBox>
            </ Div>
        </ LoginBox>     
        </ Box>
    )
}