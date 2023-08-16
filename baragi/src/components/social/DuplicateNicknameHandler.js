import { useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import Loading from "../ui/Loading";

const DuplicateNicknameHandler= () => {
    const params = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        axios.get(process.env.REACT_APP_BASE_URL + "/api/oauth2/success", {
            headers: {'content-type': 'application/json', 'Authorization' : `Bearer ${params.token}`}
        })
        .then((res) => {
            if (res.status === 200){
                console.log(res);
                navigate("/oauth2/nickname-check");
            }
        })
        .catch((res) => {
            console.log(res);
        })
    }, [])

    return (<div> 
        <Loading></Loading>
    </div>);
}

export default DuplicateNicknameHandler; 