import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { clearUser } from "../../redux/userSlice";
import axios from "axios";
import Toast from "../ui/Toast";


const LogoutHandler= () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();

    useEffect(() => {
        axios.post(process.env.REACT_APP_BASE_URL +"/api/logout",
            {
                headers : {
                    "Access-Control-Allow-Origin": process.env.REACT_APP_BASE_URL 
                },
                withCredentials : true
            }
        )
        .then((res) => {
            console.log(res);
            if (res.status === 200){
                delete axios.defaults.headers.common['Authorization'];
                window.sessionStorage.clear();
                dispatch(clearUser());
                navigate("/", {replace : true});
                Toast.fire("로그아웃되었습니다.", "", "success");
            }
        })
        .catch((res) => {
            console.log(res);
        })
    }, [])

    return (<div> 빠이요 </div>);
}

export default LogoutHandler;