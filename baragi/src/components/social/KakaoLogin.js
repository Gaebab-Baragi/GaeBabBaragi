import kakaoLoginImg from "./kakaoLoginImg.png" 
import "./socialLogin.css"

function KakaoLogin(){
    return (
        <div>
            <a href = "http://localhost:9999/oauth2/authorization/kakao">
                <img className="loginButton" src={kakaoLoginImg}></img>
            </a>
        </div>
    );
}

export default KakaoLogin;