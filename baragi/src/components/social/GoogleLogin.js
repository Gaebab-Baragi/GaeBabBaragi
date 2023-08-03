import googleLoginImg from "./googleLoginImg.png" 
import "./socialLogin.css"
import { URL, current } from "../../axios/axios"

function GoogleLogin(){
    return (
        <div>
            <a href = {URL[current] + "/oauth2/authorization/google"}>
                <img className="loginButton" src={googleLoginImg} alt="google 계정으로 로그인"></img>
            </a>
        </div>
    );
}

export default GoogleLogin;