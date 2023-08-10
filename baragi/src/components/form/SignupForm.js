import React, { useEffect, useCallback } from 'react';
import './css/BasicForm.css';
import {Routes, Route, Link, useNavigate, Outlet} from 'react-router-dom'
import { useState } from 'react';
import axios from 'axios';
import SocialLogin from '../social/SocialLogin';

function SignupForm() {
  const navigate = useNavigate();
  // 기본 input stated
  let [password1, setPassword1] = useState('');
  let [password2, setPassword2] = useState('');
  let [nickname, setNickname] = useState('');
  let [email, setEmail] = useState('');
  let [verificationCode, setVerificationCode] = useState('')
  let [code, setCode] = useState();
  let [sendCode, setSendCode] = useState(false);

  // ======================빈 값 & validation 처리 state=======================//
  let [validPassword, setValidPassword] = useState(false);
  let [samePassword, setSamePassword] = useState(false);
  let [validNickname, setValidNickname] = useState(false);
  
  useEffect(() => {
    // 정규표현식 패턴: 8자 이상 20자 이하, 영문 대소문자, 숫자, 특수문자를 하나 이상 포함
    const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,20}$/;
    const isValidPassword = passwordPattern.test(password1);
    setValidPassword(isValidPassword);
  }, [password1]);
  useEffect(()=>{
    setSamePassword(password1===password2)
  },[password2])
  useEffect(()=>{
    setValidNickname(nickname.length<=30)
  },[nickname])
  //=================================================================================//

  // ===========================확인(중복, 인증코드) state===========================//
  let [nicknameDuplicateCheck, setNicknameDuplicateCheck] = useState(false);
  let [emailCodecheck, setEmailCodeCheck] = useState(false);
  
  // handleIdDuplicateCheck, handleNicknameDuplicateCheck, handleEmailCodeCheck, handleSendCode callbacks

  const handleNicknameDuplicateCheck = useCallback((e) => {
    e.preventDefault();
    // console.log(nickname)
    console.log('Nickname-Duplication-Check')
    console.log(nickname)
    let body = JSON.stringify({
      register_name : email,
      password : password1,
      nickname : nickname,
    })

    axios.post(process.env.REACT_APP_BASE_URL +"/api/member/register/nickname", body, {
      headers: { 
        "Content-Type": `application/json; charset= UTF-8`
      }
      })
    .then((res)=>{
      if (res.status === 200) {
        console.log('duplication checked')
        alert("사용 가능한 닉네임입니다.")
        setNicknameDuplicateCheck(true);
      }
    })
    .catch((res) => {
      res = res.response;
      if (res.status === 460) alert("잘못된 닉네임 형식입니다.")
      else if (res.status === 457) alert("이미 사용중인 닉네임입니다.")
      else alert("이유를 알 수 없는 오류");
    })

  }, [nickname]);

  const handleEmailCodeCheck = useCallback((e) => {
    e.preventDefault();
    console.log('Email-Code-Check')
    if (code === verificationCode) {
      setEmailCodeCheck(true)
      alert("확인되었습니다.")
    }
    else {
      setEmailCodeCheck(false)
      alert("인증번호가 맞지 않습니다.")
    }
  }, [code]);

  const handleSendCode = useCallback((e) => {
    e.preventDefault()
    let body = JSON.stringify({
      register_name : email,
      password : password1,
      nickname : nickname,
    })

    axios.post(process.env.REACT_APP_BASE_URL +'/api/member/register/username', body, {
      headers: { "Content-Type": `application/json; charset= UTF-8`}
      })
    .then((res)=>{
      if (res.status === 200) {
        console.log('code sent')
        alert("인증 코드가 발송되었습니다. 이메일을 확인해주세요.")
        setVerificationCode(res.data);
        setEmailCodeCheck(false);
        setSendCode(true);
      }
    })
    .catch((res) => {
      console.log(res)
      res = res.response;
      if (res.status === 454) alert("잘못된 이메일입니다.")
      else if (res.status === 455) alert("이미 사용중인 이메일입니다.")
      else alert("이유를 알 수 없는 오류");
    })
  }, [email]);

  //==================================================================================//

  //--------------- 회원 가입 기능 구현-------------------//

  const handleSubmit = (e) => { 
    e.preventDefault();
    if (!nicknameDuplicateCheck || !emailCodecheck) {
      alert('중복 확인을 진행해주세요.')
    } else {
        console.log(password1);
        console.log(nickname);
        console.log(email);
        
        let body = {
          registerName : email,
          password : password1,
          nickname : nickname,
        };
        axios.post(process.env.REACT_APP_BASE_URL +'/api/member/register', body)
        .then((res)=>{
          if (res.status ===201) {
            console.log('signup success')
            navigate('/login')
            alert("회원 가입에 성공했습니다.")
          }
        })
        .catch((res) => {
          console.log(res);
          alert("잘못된 회원가입입니다.")
        }
        );
      }
    }

  //----------------------------------------------------------//
  return (
    <div className="formContainer">
      <form  className="form">
        <div className="formTitle">회원가입</div>

        {/* 이메일 입력 & 인증번호 요청하기 */}
        <div className="formGroup" id='checkGroup'>
          <div className="formGroupComponent">
            <input className='formInput' onChange={e=>{setEmail(e.target.value); setSendCode(false);}} type="email" id='formEmail' placeholder='이메일을 입력해주세요.' />
            <button onClick={handleSendCode} className='emailVerificationButton'>인증코드 발송</button>
          </div>
        </div>

        {/* 인증코드 입력 */}
        { sendCode && (
        <div className="formGroup">
          <div className="formGroupComponent">
            <input className='formInput' onChange={e=>{setCode(e.target.value);}} type="text"  placeholder="인증번호 입력" />
            <button onClick={handleEmailCodeCheck} className='verificationBtn'>확인</button>
          </div>
        </div>
        )
        }
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

        {/* 닉네임 입력 */}
        <div className="formGroup">
          <div className="formGroupComponent">
            <input className='formInput' onChange={e=>{setNickname(e.target.value); setNicknameDuplicateCheck(false);}} type="text" id='formNickname' placeholder='닉네임을 입력해주세요'/>
            <button className='duplicationCheckButton' onClick={handleNicknameDuplicateCheck}>중복 확인</button>
          </div>
          { !validNickname && nickname.length>=1 && (
            <div className="errorMsg">닉네임은 1~30자로 작성해주세요.</div>
          )}
        </div>



        {/* 로그인 navigate */}
        <div className='formGroup'>
          이미 회원이신가요?<span className='loginRoute' onClick={()=>{navigate('/login')}}>로그인</span> 
        </div>

        {/* Signup 버튼 */}
        <button onClick={handleSubmit}  className="submitButton" type="submit">
          SIGNUP
        </button>

        <hr />

        {/* 구글 로그인 */}
        <div className="formGroup">
            <SocialLogin/>
        </div>
      </form>
    </div>
  );
}

export default SignupForm;