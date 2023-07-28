import googleLoginImg from "./googleLoginImg.png" 
import "./socialLogin.css"

function GoogleLogin(){
    return (
        <div>
            <a href = "http://localhost:9999/oauth2/authorization/google">
                <img className="loginButton" src={googleLoginImg}></img>
            </a>
        </div>
    );
}

export default GoogleLogin;