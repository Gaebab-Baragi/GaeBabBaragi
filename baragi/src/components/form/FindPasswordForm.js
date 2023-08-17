import React from 'react';
import { useNavigate } from 'react-router-dom'
import { useState, useCallback } from 'react';
import './css/BasicForm.css'
import axios from 'axios';
import Toast from '../ui/Toast';

function FindPasswordForm() {
  const navigate = useNavigate();
  const [email, setEmail] = useState();
  const [verificationCode, setVerificationCode] = useState('')
  const [code, setCode] = useState();
  const [sendCode, setSendCode] = useState(false);
  const [emailCodecheck, setEmailCodeCheck] = useState(false);

  const handleEmailCodeCheck = useCallback((e) => {
    e.preventDefault();
    if (code === verificationCode) {
      setEmailCodeCheck(true)
      Toast.fire("이메일 인증에 성공했습니다.", "", "success")
    }
    else {
      setEmailCodeCheck(false)
      Toast.fire("이메일 인증에 실패했습니다.", "", "error");
    }
  }, [code]);

  const handleSendCode = useCallback(async (e) => {
    e.preventDefault()

    await axios.get(process.env.REACT_APP_BASE_URL +`/api/member/find?email=${email}`)
    .then((res) => {
      if (res.status === 200){
        // console.log('code sent')
        Toast.fire("인증 코드가 발송되었습니다. 이메일을 확인해주세요.", "", "info");
        setVerificationCode(res.data);
        setEmailCodeCheck(false);
        setSendCode(true);
      }
      // console.log(res);
    })
    .catch((res) => {
      res = res.response;
      if (res.status === 454) Toast.fire("잘못된 이메일입니다.", "", "error");
      if (res.status === 462)  Toast.fire("가입되지 않은 이메일입니다.", "", "warning");
    })
  }, [email]);
    
  const handleSubmit = async (e) => { 
    e.preventDefault();
    if (!emailCodecheck) {
      Toast.fire("이메일 인증을 완료해주세요", "", "error");
    } else {
      await axios.post(process.env.REACT_APP_BASE_URL +"/api/member/reset-password", { email },
        {
          headers: {"Content-Type": `application/json; charset= UTF-8`}
        })
        .then((res)=>{
        if (res.status === 200) {
          Toast.fire("이메일로 새 비밀번호를 발송했습니다.", "", "success");
          navigate("/login");
        }
        })
        .catch((res) => {
          Toast.fire("비밀번호 재발급에 실패했습니다.", "", "error");
        })
    }
  } 

  return (
    <div className="formContainer">
      <form className="form">
        <div className="findFormTitle">
          <span className='currentPage'>비밀번호 찾기</span>
        </div>

        {/* 이메일 입력  */}
        <div className="formGroup">
          <div className="formGroupComponent">
            <input className='formInput' onChange={e=>{setEmail(e.target.value); setSendCode(false);}} type="email"  placeholder="이메일 입력" />
            <button onClick={handleSendCode} className='sendCodeBtn'>인증코드 보내기</button>
          </div>
        </div>

        {/* 인증번호 입력 */}
        { sendCode && (
        <div className="formGroup">
          <div className="formGroupComponent">
            <input className='formInput' onChange={e=>{setCode(e.target.value);}} type="text"  placeholder="인증번호 입력" />
            <button onClick={handleEmailCodeCheck} className='verificationBtn'>확인</button>
          </div>
        </div>
        )}

        {/* Login 버튼 */}
        <button className="submitButton" type="submit" onClick={handleSubmit}>
          비밀번호 찾기
        </button>

      </form>
    </div>
  );

}

export default FindPasswordForm;