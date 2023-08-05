import googleLoginImg from "./googleLoginImg.png" 
import "./socialLogin.css"

function GoogleLogin(){
    return (
        <div>
            <a href = {"http://localhost:8083/api/oauth2/authorization/google"}>
                <img className="loginButton" src={googleLoginImg} alt="google 계정으로 로그인"></img>
            </a>
        </div>
    );
}

export default GoogleLogin;