import googleLoginImg from "./googleLoginImg.png" 
import "./socialLogin.css"

function GoogleLogin(){
    return (
        <div>
            {/* {console.log(process.env.REACT_APP_BASE_URL)} */}
            <a href = {process.env.REACT_APP_BASE_URL + "/api/oauth2/authorization/google"}>
                <img className="loginButton" src={googleLoginImg} alt="google 계정으로 로그인"></img>
            </a>
        </div>
    );
}

export default GoogleLogin;