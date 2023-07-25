import React from 'react';
import './BasicForm.css';
import {Routes, Route, Link, useNavigate, Outlet} from 'react-router-dom'
import { useState } from 'react';
import axios from 'axios';

function SignupForm() {
  const navigate = useNavigate();
  let [id, setId] = useState('');
  let [password1, setPassword1] = useState('');
  let [password2, setPassword2] = useState('');
  let [nickname, setNickname] = useState('');
  let [email, setEmail] = useState('');
  let [code, setCode] = useState();
  let [sendCode, setSendCode] = useState(false);

  // 아이디 중복 체크 function
  const handleIdDuplicateCheck = ()=>{
    alert('중복확인필요');
  }
  // 닉네임 중복 체크 function
  const handleNicknameDuplicateCheck = ()=>{
    alert('닉네임 확인')
  }

  // 인증번호 입력창 보여주기 위해 state 변경
  const handleSendCode = () => {
    setSendCode(true);
  }

  //--------------- 회원 가입 기능 구현-------------------//
  // 다른 버튼 누를 때 axois 요청되는 문제 해결하기
  // 빈 값 있을 때 오류 msg 보여주기
  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(password1);
    console.log(id);
    console.log(nickname);
    console.log(email);

    let body = {
      registerName : id,
      password : password1,
      nickname : nickname,
      email : email
    };
    // axios.post('/member/register', body)
    // .then((res)=>{
    //   if (res.data.code ===200) {
    //     console.log('signup success')
    //     navigate('/login')
    //   }
    // })
  }
  //----------------------------------------------------------//


  return (
    <div className="formContainer">
      <form onSubmit={handleSubmit} className="form">
        <div className="formTitle">회원가입</div>

        {/* 아이디 입력  */}
        <div className="formGroup" id='checkGroup'>
          <input className='formInput' onChange={e=>{setId(e.target.value)}} type="id" id="formId" placeholder="아이디를 입력해주세요." />
          <button className='duplicationCheckButton' onClick={()=>handleIdDuplicateCheck()}>중복 확인</button>
        </div>

        {/* 비밀번호1 입력 */}
        <div className="formGroup">
          <input className='formInput' onChange={e=>{setPassword1(e.target.value)}} type="password" id="formPassword1" placeholder="비밀번호를 입력해주세요." />
        </div>
        {/* 비밀번호2 다시 입력 */}
        <div className="formGroup">
          <input className='formInput' onChange={e=>{setPassword2(e.target.value)}} type="password" id="formPassword2" placeholder="비밀번호를 다시 입력해주세요." />
        </div>

        {/* 닉네임 입력 */}
        <div className="formGroup">
          <input className='formInput' onChange={e=>{setNickname(e.target.value)}} type="text" id='formNickname' placeholder='닉네임을 입력해주세요'/>
          <button className='duplicationCheckButton' onClick={()=>handleNicknameDuplicateCheck()}>중복 확인</button>
        </div>

        {/* 이메일 입력 & 인증번호 요청하기 */}
        <div className="formGroup" id='checkGroup'>
          <input className='formInput' onChange={e=>{setEmail(e.target.value)}} type="email" id='formEmail' placeholder='이메일을 입력해주세요.' />
          <button onClick={()=>handleSendCode()} className='emailVerificationButton'>인증코드 발송</button>
        </div>

        {/* 인증코드 입력 */}
        { sendCode && (
        <div className="formGroup">
          <input className='formInput' onChange={e=>{setCode(e.target.value)}} type="text"  placeholder="인증번호 입력" />
          <button className='verificationBtn'>확인</button>
        </div>
        )
        }

        {/* 로그인 navigate */}
        <div className='formGroup'>
          이미 회원이신가요?<span className='loginRoute' onClick={()=>{navigate('/login')}}>로그인</span> 
        </div>

        {/* Signup 버튼 */}
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