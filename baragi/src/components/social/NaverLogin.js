import naverLoginImg from "./naverLoginImg.png" 
import "./socialLogin.css"

function NaverLogin(){
    return (
        <div>
            <a href = "http://localhost:8083/api/oauth2/authorization/naver">
                <img className = "loginButton" src={naverLoginImg} alt="네이버 계정으로 로그인"></img>
            </a>
        </div>
    )
}
export default NaverLogin;