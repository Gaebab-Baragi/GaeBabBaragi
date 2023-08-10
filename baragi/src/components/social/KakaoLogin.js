import kakaoLoginImg from "./kakaoLoginImg.png" 
import "./socialLogin.css"

const KakaoLogin = () =>{
    return (
        <div>
            <a href = {process.env.REACT_APP_BASE_URL + "/api/oauth2/authorization/kakao"}>
                <img className = "loginButton" src={kakaoLoginImg} alt="naver"></img>
            </a>
        </div>
    );
}

export default KakaoLogin;