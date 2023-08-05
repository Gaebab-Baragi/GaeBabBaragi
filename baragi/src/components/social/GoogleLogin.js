import googleLoginImg from "./googleLoginImg.png" 
import "./socialLogin.css"

function GoogleLogin(){
    return (
        
        <div>
                <img className="loginButton" src={googleLoginImg} alt="google 계정으로 로그인" onClick={test}></img>
        </div>
    );
}

export default GoogleLogin;