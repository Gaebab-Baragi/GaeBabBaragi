import { useEffect } from "react";
import { useDispatch } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import { loginUser } from "../../redux/userSlice";
import axios from "axios";

const SocialLoginHandler= () => {

    const params = useParams();
    const navigate = useNavigate();
    const dispatch = useDispatch();

    useEffect(() => {
        axios.get( "/api/oauth2/success", {
            params : {token : params.token},
            headers: {'content-type': 'application/json'}
        }
        )
        .then((res) => {
            if (res.status === 200){
                const data = {...res.data, accessToken:params.token}
                dispatch(loginUser(data));
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