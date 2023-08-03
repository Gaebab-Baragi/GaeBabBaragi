import instance from "../../axios/axios";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { clearUser } from "../../redux/userSlice";

const LogoutHandler= () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();

    useEffect(() => {
        instance.post("/logout")
        .then((res) => {
            console.log(res);
            if (res.status === 200){
                window.sessionStorage.clear();
                dispatch(clearUser());
                alert("로그아웃되었습니다.");
                navigate("/", {replace : true});
            }
        })
        .catch((res) => {
            console.log(res);
        })
    }, [])

    return (<div> 빠이요 </div>);
}

export default LogoutHandler;