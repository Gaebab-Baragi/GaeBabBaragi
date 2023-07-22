import React from 'react';
import './Form.css';
import {Routes, Route, Link, useNavigate, Outlet} from 'react-router-dom'
import { useState } from 'react';

function LoginForm() {
  const navigate = useNavigate();
  let [id, setId] = useState();
  let [password, setPassword] = useState();

  return (
    <div className="formContainer">
      <form className="form">
        <div className="formTitle">로그인</div>

        {/* 아이디 입력  */}
        <div className="formGroup">
          <input onChange={e=>{setId(e.target.value)}} type="id" id="formBasicEmail" placeholder="아이디를 입력해주세요." />
        </div>

        {/* 비밀번호 입력 */}
        <div className="formGroup">
          <input onChange={e=>{setPassword(e.target.value)}} type="password" id="formBasicPassword" placeholder="비밀번호를 입력해주세요." />
        </div>

        {/* 아이디 찾기 / 비밀번호 찾기 / 회원가입 */}
        <div className='formGroup'>
          <span onClick={()=>{navigate('/find-id')}}>아이디 찾기</span> | <span onClick={()=>{navigate('/find-password')}}>비밀번호 찾기</span> | <span onClick={()=>navigate('/signup')}>회원가입</span>
        </div>

        {/* Login 버튼 */}
        <button className="loginButton" type="submit">
          LOGIN
        </button>

        {/* OR  */}
        <div className="formGroup">
          <hr />
        </div>

        {/* 구글 로그인 */}
        <div className="formGroup">
          구글 로그인 임
        </div>
      </form>
    </div>
  );
}

export default LoginForm;

// 아이디, 비밀번호로 로그인 function 만들기
// 어떻게? back-end랑 얘기해보기