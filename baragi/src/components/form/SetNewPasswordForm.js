import { useState, useEffect } from "react";
import Toast from "../ui/Toast";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const SetNewPasswordForm = ({password}) => {
    const [originPassword, setOriginPassword] = useState('')
    const [password1, setPassword1] = useState('');
    const [password2, setPassword2] = useState('');
    const [validPassword, setValidPassword] = useState(false);
    const [samePassword, setSamePassword] = useState(false);
    const navigate = useNavigate();

    useEffect(() => {
        setOriginPassword(password);
    }, [password])

    useEffect(() => {
        // 정규표현식 패턴: 8자 이상 20자 이하, 영문 대소문자, 숫자, 특수문자를 하나 이상 포함
        const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,20}$/;
        const isValidPassword = passwordPattern.test(password1);
        setValidPassword(isValidPassword);
    }, [password1]);

    useEffect(()=>{
        setSamePassword(password1===password2)
    },[password2])

    const handleSubmit = (e) => { 
        e.preventDefault();
        if (!validPassword || !samePassword) {
            Toast.fire('비밀번호를 확인해주세요', "", "error")
            return
        } 

        axios.post(process.env.REACT_APP_BASE_URL +'/api/member/modify/password', { originPassword, password : password1})
        .then((res)=>{
            if (res.status ===200) {
                Toast.fire("비밀번호를 변경했습니다.", "", "success")
                navigate('/')
            } 
        })
        .catch((res) => {
            Toast.fire("비밀번호를 수정할 수 없습니다.", "", "error")
        });
    }

    return (
    <div className="formContainer">
      <form  className="form">
        <div className="formTitle">비밀번호 변경</div>

        {/* 비밀번호1 입력 */}
        <div className="formGroup">
          <div className="formGroupComponent">
            <input className='formInput' onChange={e=>{setPassword1(e.target.value)}} type="password" id="formPassword1" placeholder="비밀번호를 입력해주세요." />
          </div>
          { !validPassword && password1.length>=1 && (
            <div className='errorMsg'>비밀번호는 특수문자, 숫자, 영문자가 각각 하나 이상 포함되게 8자 이상으로 작성해주세요. </div>
          )}
        </div>
        {/* 비밀번호2 다시 입력 */}
        <div className="formGroup">
          <div className="formGroupComponent">
            <input className='formInput' onChange={e=>{setPassword2(e.target.value)}} type="password" id="formPassword2" placeholder="비밀번호를 다시 입력해주세요." />
          </div>
          {!samePassword && password2.length>=1 &&(
            <div className="errorMsg">비밀번호가 일치하지 않습니다.</div>
          )}
        </div>

        {/* 수정 버튼 */}
        <button onClick={handleSubmit}  className="submitButton" type="submit">
            수정하기
        </button>

        <hr />
      </form>
    </div>
    );
}

export default SetNewPasswordForm;