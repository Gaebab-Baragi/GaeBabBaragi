import React from 'react';
import './BasicForm.css';
import {Routes, Route, Link, useNavigate, Outlet} from 'react-router-dom'
import { useState } from 'react';

function SignupForm() {
  const navigate = useNavigate();
  let [id, setId] = useState();
  let [password1, setPassword1] = useState();
  let [password2, setPassword2] = useState();
  let [nickname, setNickname] = useState();
  let [email, setEmail] = useState();

  // 아이디 중복 체크 function
  const handleDuplicateCheck = ()=>{
    alert('중복확인필요');
  }

  return (
    <div className="formContainer">
      <form className="form">
        <div className="formTitle">회원가입</div>

        {/* 아이디 입력  */}
        <div className="formGroup" id='checkGroup'>
          <input onChange={e=>{setId(e.target.value)}} type="id" id="formId" placeholder="아이디를 입력해주세요." />
          <button className='duplicationCheckButton' onClick={handleDuplicateCheck}>중복 확인</button>
        </div>

        {/* 비밀번호1 입력 */}
        <div className="formGroup">
          <input onChange={e=>{setPassword1(e.target.value)}} type="password" id="formPassword" placeholder="비밀번호를 입력해주세요." />
        </div>
        {/* 비밀번호2 다시 입력 */}
        <div className="formGroup">
          <input onChange={e=>{setPassword2(e.target.value)}} type="password" id="formPassword" placeholder="비밀번호를 다시 입력해주세요." />
        </div>

        {/* 닉네임 입력 */}
        <div className="formGroup">
          <input onChange={e=>{setNickname(e.target.value)}} type="text" id='formNickname' placeholder='닉네임을 입력해주세요'/>
        </div>

        {/* 이메일 입력 */}
        <div className="formGroup" id='checkGroup'>
          <input onChange={e=>{setEmail(e.target.value)}} type="email" id='formEmail' placeholder='이메일을 입력해주세요.' />
          <button className='emailVerificationButton'>인증코드 발송</button>
        </div>


        {/* 로그인 navigate */}
        <div className='formGroup'>
          이미 회원이신가요?<span className='loginRoute' onClick={()=>{navigate('/login')}}>로그인</span> 
        </div>

        {/* Login 버튼 */}
        <button className="submitButton" type="submit">
          SIGNUP
        </button>

        <hr />

        {/* 구글 로그인 */}
        <div className="formGroup">
          구글 로그인 임
        </div>
      </form>
    </div>
  );



}

export default SignupForm;

// 남은 일
// 1. 중복 확인 function
// 2. 이메일 인증 코드 function
// 3. 회원가입 function