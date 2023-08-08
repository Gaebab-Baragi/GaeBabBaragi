import React from 'react';
import {Routes, Route, Link, useNavigate, Outlet} from 'react-router-dom'
import { useState } from 'react';
import './css/BasicForm.css'

function FindIdForm() {
  const navigate = useNavigate();
  let [email, setEmail] = useState();
  let [password, setPassword1] = useState();

  const sendAuthenticationCode = () =>{
    console.log('인증코드 보내기')
  }

  return (
    <div className="formContainer">
      <form className="form">
        <div className="findFormTitle">
          <span className='currentPage'>아이디 찾기</span> |  <span className='pointerShow' onClick={()=>navigate('/find-password')}>비밀번호 찾기</span>
        </div>

        {/* 이메일 입력  */}
        <div className="formGroup">
          <div className="formGroupComponent">
            <input className='formInput' onChange={e=>{setEmail(e.target.value)}} type="email" id="formId" placeholder="이메일 입력" />
            <button onClick={sendAuthenticationCode()} className='sendCodeBtn'>인증코드 보내기</button>
          </div>
        </div>

        {/* 인증번호 입력 */}
        <div className="formGroup">
          <div className="formGroupComponent">
            <input className='formInput' onChange={e=>{setPassword1(e.target.value)}} type="text" id="formPassword" placeholder="인증번호 입력" />
            <button className='verificationBtn'>확인</button>
          </div>
        </div>

        {/* Login 버튼 */}
        <button className="submitButton" type="submit">
          아이디 찾기
        </button>

      </form>
    </div>
  );



}

export default FindIdForm;