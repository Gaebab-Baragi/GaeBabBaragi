import axios from "axios";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { clearUser } from "../../redux/userSlice";

const LogoutHandler= () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();

    useEffect(() => {
        axios.post("/member/logout", {
            headers: {'content-type': 'application/json'}
        }
        )
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