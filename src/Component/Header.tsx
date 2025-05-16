import styled from "styled-components";
import { useNavigate } from "react-router-dom";


const Div = styled.div`
    width: 100vw;
    height: 80px;
    display: flex;
    justify-content: space-between;
    color: green;
    padding-left: 150px;
    padding-right: 150px;
    box-sizing: border-box;
    align-items:center;
`

const HeaderLine = styled.div`
    width: 100%;
    height: 3px;
    background-color: green;
`

const H = styled.h1`
    margin: 0;
    cursor: pointer;
`
const P = styled.p`
    padding-right: 20px;
    box-sizing: border-box;
    font-weight: bold;
    cursor: pointer;
`
const Option = styled.div`
        display: flex;

`
interface HeaderProps {
    userLogin: boolean;
  }
  
export default function Header({userLogin} : HeaderProps){
   
    const navigate = useNavigate();

    const signUpButton = () => {
        navigate("/SignUp");
    }

    const loginButton = () => {
        navigate("/Login");
    }

    const logoutButton = () => {
        localStorage.removeItem('JWT');
        navigate("/Login");
    }

    const mainButton = () => {
        navigate("/Main");
    }

    const writeButton = () => {
        navigate("/Write")
    }

    const myPageButton = () => {
        navigate("/MyPage");    
    }
    return(
        <>
            <Div>
                <H onClick={mainButton}>SAS</H>
                { userLogin ? (
                    <Option>
                    <P onClick={logoutButton}>로그아웃</P>
                    <P onClick={myPageButton}>내정보</P>
                    <P onClick={writeButton}>글쓰기</P> 
                    </ Option>
                ):(
                    <Option>
                    <P onClick={signUpButton}>회원가입</P>
                    <P onClick={loginButton}>로그인</P>
                    </ Option>
                )} 
            </ Div>
            <HeaderLine></ HeaderLine>
        </>
    );
}