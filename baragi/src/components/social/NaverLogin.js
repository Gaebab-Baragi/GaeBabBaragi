import naverLoginImg from "./naverLoginImg.png" 
import "./socialLogin.css"

function NaverLogin(){
    return (
        <div>
            <a href = "http://localhost:9999/oauth2/authorization/naver">
                <img className = "loginButton" src={naverLoginImg}></img>
            </a>
        </div>
    );
}

export default NaverLogin;