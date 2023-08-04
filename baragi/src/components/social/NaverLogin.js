import naverLoginImg from "./naverLoginImg.png" 
import "./socialLogin.css"
import { URL, current } from "../../axios/axios"

function NaverLogin(){
    return (
        <div>
            <a href = {URL[current] + "/api/oauth2/authorization/naver"}>
                <img className = "loginButton" src={naverLoginImg} alt="네이버 계정으로 로그인"></img>
            </a>
        </div>
    )
}

export default NaverLogin;