import kakaoLoginImg from "./kakaoLoginImg.png" 
import "./socialLogin.css"

const KakaoLogin = () =>{
    return (
        <div>
            <a href = "/api/oauth2/authorization/kakao">
                <img className = "loginButton" src={kakaoLoginImg} alt="naver"></img>
            </a>
        </div>
    );
}

export default KakaoLogin;