import kakaoLoginImg from "./kakaoLoginImg.png" 
import "./socialLogin.css"
import { URL, current } from "../../axios/axios"
function KakaoLogin(){
    return (
        <div>
            <a href = {URL[current] + "/oauth2/authorization/kakao"}>
                <img className="loginButton" src={kakaoLoginImg}></img>
            </a>
        </div>
    );
}

export default KakaoLogin;