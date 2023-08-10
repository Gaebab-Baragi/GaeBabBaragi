import GoogleLogin from "./GoogleLogin";
import KakaoLogin from "./KakaoLogin";
import NaverLogin from "./NaverLogin";

const SocialLogin = () => {
  return (
    <div>
      <GoogleLogin />
      <NaverLogin />
      <KakaoLogin />
    </div>
  );
};

export default SocialLogin;
