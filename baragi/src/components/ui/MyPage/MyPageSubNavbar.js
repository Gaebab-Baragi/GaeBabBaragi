import "./MyPageNav.css"
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from 'react';

const MyPageSubNavBar = ({sel}) => {
    const navigate = useNavigate();
    const [selected, setSelected] = useState(1);

    useEffect(() => {
        setSelected(sel);
    }, [])

    return (
        <>
            <div className="my-page-nav-sub-container">
                <span className={"my-page-info " + (selected===1 ? "my-selected" : "")} onClick={() => {navigate('/myinformation'); setSelected(1)}}> 프로필 변경 </span>
                <span className={"my-page-info " + (selected===2 ? "my-selected" : "")} onClick={() => {navigate('/password-modification'); setSelected(2)}}> 비밀번호 변경 </span>
            </div>
        </>
    )
}

export default MyPageSubNavBar;