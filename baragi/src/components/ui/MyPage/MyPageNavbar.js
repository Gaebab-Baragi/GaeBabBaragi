import { useEffect, useState } from 'react';
import './MyPageNav.css'
import { useNavigate } from "react-router-dom";


const MyPageNavBar = ({sel}) => {
    const navigate = useNavigate();
    const [selected, setSelected] = useState(1);

    useEffect(() => {
        setSelected(sel);
    }, [])

    return (
        <>
            <div className="my-page-nav-container">
                <span className={"my-page-big " + (selected===1 ? "my-selected" : "")} onClick={() => {navigate('/myinformation'); setSelected(1)}}> 정보 변경 </span>
                <span className={"my-page-big " + (selected===2 ? "my-selected" : "")} onClick={() => {navigate('/my-pet-list/0'); setSelected(2)}}> 반려견 관리 </span>
                <span className={"my-page-big " + (selected===3 ? "my-selected" : "")} onClick={() => {navigate('/myrecipe'); setSelected(3)}}> 내 레시피 </span>
            </div>
        </>
    );
}

export default MyPageNavBar;