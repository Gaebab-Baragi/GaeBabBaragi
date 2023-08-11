import { useState } from "react";
import Toast from "../ui/Toast";
import axios from "axios";

const CurrentPasswordCheckForm = ({setCurrentPassword, setIsChecked}) => {

    const [password, setPassword] = useState('');

    const onSubmit = (e) => {
        e.preventDefault();
        if (!password) {
            Toast.fire("현재 비밀번호를 입력해주세요", "", "warning");
            return;
        }

        axios.post(process.env.REACT_APP_BASE_URL +'/api/member/modify/check-password', 
            {password}, 
            {headers: {
                'Content-Type' : 'application/json; charset=utf-8'
            }}) 
        .then((res) => {
            if (res.status === 200){
                Toast.fire("비밀번호가 확인되었습니다", "", "success");
                setCurrentPassword(password);
                setIsChecked(true);
            }
            else {
                Toast.fire("비밀번호를 확인할 수 없습니다.", "", "error")
            }
        })
        .catch ((err) => {
            err = err.response;
            if (err.status === 453) Toast.fire("비밀번호가 일치하지 않습니다.", "", "warning")
            else {
                Toast.fire("비밀번호를 확인할 수 없습니다.", "", "error")
            }
        })        

    }

    return (
    <div className="formContainer">
      <form onSubmit={onSubmit} className="form">
        <div className="formTitle">비밀번호 확인</div>

        {/* 비밀번호 입력 */}
        <div className="formGroup">
          <input className='formInput' onChange={e=>{setPassword(e.target.value)}} type="password" id="formBasicPassword" placeholder="현재 비밀번호를 입력해주세요." />
        </div>

        <button className="submitButton" type="submit">
            비밀번호 확인
        </button>
      </form>
    </div>
    )
}

export default CurrentPasswordCheckForm;