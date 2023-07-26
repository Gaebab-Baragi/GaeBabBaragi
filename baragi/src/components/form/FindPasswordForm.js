import React from 'react';
import {Routes, Route, Link, useNavigate, Outlet} from 'react-router-dom'
import { useState } from 'react';
import './BasicForm.css'

function FindPasswordForm() {
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
          <span className='pointerShow' onClick={()=>navigate('/find-id')}>아이디 찾기</span> | <span className='currentPage'>비밀번호 찾기</span>
        </div>

        {/* 아이디 입력 */}
        <div className="formGroup">
          <input className='formInput' type="id"  placeholder='아이디 입력'/>
        </div>

        {/* 이메일 입력  */}
        <div className="formGroup">
          <div className="formGroupComponent">
            <input className='formInput' onChange={e=>{setEmail(e.target.value)}} type="email"  placeholder="이메일 입력" />
            <button onClick={sendAuthenticationCode()} className='sendCodeBtn'>인증코드 보내기</button>
          </div>
        </div>

        {/* 인증번호 입력 */}
        <div className="formGroup">
          <div className="formGroupComponent">
            <input className='formInput' onChange={e=>{setPassword1(e.target.value)}} type="text" id="formPassword" placeholder="인증번호 입력"/>
            <button className='verificationBtn'>확인</button>
          </div>
        </div>

        {/* Login 버튼 */}
        <button className="submitButton" type="submit">
          비밀번호 찾기
        </button>

      </form>
    </div>
  );

}

export default FindPasswordForm;