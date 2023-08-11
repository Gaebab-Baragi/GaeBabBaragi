import { useEffect, useState } from 'react';
import './MyPageNav.css'
import { useNavigate } from "react-router-dom";


const MyPageNavBar = () => {
    const navigate = useNavigate();
    const [selected, setSelected] = useState('');

    return (
        <div className="my-page-big">
            <div>
                <span className="my-page-big" onClick={() => navigate('/myinformation')}> 정보 변경 </span>
                <span className="my-page-big" onClick={() => navigate('/my-pet-list/0')}> 반려견 등록 </span>
                <span className="my-page-big" onClick={() => navigate('/myrecipe')}> 내 레시피</span>
            </div>
        </div>
    );
}

export default MyPageNavBar;