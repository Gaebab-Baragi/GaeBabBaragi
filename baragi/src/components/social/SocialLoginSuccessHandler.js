import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { loginUser } from "../../redux/userSlice";
import axios from "axios";
import Loading from "../ui/Loading";

const SocialLoginHandler= () => {

    const params = useParams();
    const navigate = useNavigate();
    const dispatch = useDispatch();

    useEffect(() => {
        axios.get( process.env.REACT_APP_BASE_URL +"/api/oauth2/success", {
            headers: {'content-type': 'application/json', 'Authorization' : `Bearer ${params.token}`}
        }
        )
        .then((res) => {
            if (res.status === 200){
                dispatch(loginUser(res.data));
                console.log("handler", res.data);
                navigate("/");
            }
        })
        .catch((res) => {
            console.log(res);
        })
    }, [])

    return (
        <>
            안녕하이
            <Loading></Loading>
        </>
    ) 
}

export default SocialLoginHandler;