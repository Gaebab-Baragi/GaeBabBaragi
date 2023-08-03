import axios from "../../axios/axios";
import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { loginUser } from "../../redux/userSlice";



const SocialLoginHandler= () => {

    const params = useParams();
    const navigate = useNavigate();
    const dispatch = useDispatch();

    useEffect(() => {
        axios.get( "/oauth2/success", {
            params : {token : params.token},
            headers: {'content-type': 'application/json'}
        }
        )
        .then((res) => {
            if (res.status === 200){
                const accessToken = params.token;
                axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
                dispatch(loginUser(res.data));
                navigate("/");
            }
        })
        .catch((res) => {
            console.log(res);
        })
    }, [])

    return (<div> 안녕 </div>);
}

export default SocialLoginHandler;