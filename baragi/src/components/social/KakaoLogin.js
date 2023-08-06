import kakaoLoginImg from "./kakaoLoginImg.png" 
import "./socialLogin.css"

const KakaoLogin = () =>{
    return (
        <div>
            <a href = "http://localhost:8083/api/oauth2/authorization/kakao">
                <img className = "loginButton" src={kakaoLoginImg} alt="naver"></img>
            </a>
        </div>
    );
}

export default KakaoLogin;