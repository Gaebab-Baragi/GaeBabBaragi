import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
import axios from "axios";

const KakaoCallback = (props) =>{
    const navigate = useNavigate();
    const code = new URL(window.location.href).searchParams.get("code");
    const REDIRECT_URI = `http://localhost:3000${process.env.REACT_APP_REDIRECT_URI}kakao`

    //인가코드 백으로 보내는 코드
    useEffect(() => {
        const kakaoLogin = async () => {
            await axios({
            method: "GET",
            url: `${REDIRECT_URI}?code=${code}`,
            headers: {
                "Content-Type": "application/json;charset=utf-8",
                "Access-Control-Allow-Origin": "*", //이건 cors 에러때문에 넣어둔것. 당신의 프로젝트에 맞게 지워도됨
            },
            }).then((res) => { //백에서 완료후 우리사이트 전용 토큰 넘겨주는게 성공했다면
            console.log(res);
            //계속 쓸 정보들( ex: 이름) 등은 localStorage에 저장해두자
            localStorage.setItem("name", res.data.account.kakaoName);
            //로그인이 성공하면 이동할 페이지
            navigate("/owner-question");
            });
        };
        kakaoLogin();
    }, [props.history]);
    return (
        <></>


    );
}

export default KakaoCallback