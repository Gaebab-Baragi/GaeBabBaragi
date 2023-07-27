import React, { useEffect, useCallback } from 'react';
import './BasicForm.css';
import {Routes, Route, Link, useNavigate, Outlet} from 'react-router-dom'
import { useState } from 'react';
import axios from 'axios';

function SignupForm() {
  const navigate = useNavigate();
  // 기본 input stated
  const [id, setId] = useState('');
  const [password1, setPassword1] = useState('');
  const [password2, setPassword2] = useState('');
  const [nickname, setNickname] = useState('');
  const [email, setEmail] = useState('');
  const [code, setCode] = useState();
  const [sendCode, setSendCode] = useState(false);

  // ======================빈 값 & validation 처리 state=======================//
  let [validId, setValidId] = useState(false);
  let [validPassword, setValidPassword] = useState(false);
  let [samePassword, setSamePassword] = useState(false);
  let [validNickname, setValidNickname] = useState(false);
  let [validEmail, setValidEmail] = useState(false); 

  useEffect(() => {
    // 정규표현식 패턴: 5자 이상 20자 이하, 영어 소문자, 숫자, 특수문자를 하나 이상 포함
    const idPattern = /^(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-z0-9!@#$%^&*]{5,20}$/i;
    const isValidId = idPattern.test(id);
    setValidId(isValidId);
    // console.log(id)
  }, [id]);
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
    setValidNickname(nickname.length<=10)
  },[nickname])
  //=================================================================================//

  // ===========================확인(중복, 인증코드) state===========================//
  let [idDuplicateCheck, setIdDuplicateCheck] = useState(false);
  let [nicknameDuplicateCheck, setNicknameDuplicateCheck] = useState(false);
  let [emailCodecheck, setEmailCodeCheck] = useState(false);
  
  // handleIdDuplicateCheck, handleNicknameDuplicateCheck, handleEmailCodeCheck, handleSendCode callbacks
  const handleIdDuplicateCheck = useCallback((e) => {
    e.preventDefault();
    // console.log(id)
    console.log('idDuplicationCheck')
    // 
    setIdDuplicateCheck(true);
  }, [id]);

  const handleNicknameDuplicateCheck = useCallback((e) => {
    e.preventDefault();
    // console.log(nickname)
    console.log('Nickname-Duplication-Check')
    setNicknameDuplicateCheck(true);
  }, [nickname]);

  const handleEmailCodeCheck = useCallback((e) => {
    e.preventDefault();
    console.log('Email-Code-Check')
    setEmailCodeCheck(true);
  },[code]);

  const handleSendCode = useCallback((e) => {
    e.preventDefault()
    setSendCode(true);
  }, []);

  //==================================================================================//

  //--------------- 회원 가입 기능 구현-------------------//
  // 다른 버튼 누를 때 axois 요청되는 문제 해결하기

  const handleSubmit = (e) => { 
    e.preventDefault();
    if (!idDuplicateCheck || !nicknameDuplicateCheck || !emailCodecheck) {
      alert('중복 확인을 진행해주세요.')
    } else {
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
        axios.post('/member/register', body)
        .then((res)=>{
          if (res.data.code ===200) {
            console.log('signup success')
            navigate('/login')
          }
        })
      }
    }
  //----------------------------------------------------------//


  return (
    <div className="formContainer">
      <form  className="form">
        <div className="formTitle">회원가입</div>

        {/* 아이디 입력  */}
        <div className="formGroup" id='checkGroup'>
          <div className='formGroupComponent'>
            <input className='formInput' onChange={e=>{setId(e.target.value)}} type="id" id="formId" placeholder="아이디를 입력해주세요." />
            <button className='duplicationCheckButton' onClick={handleIdDuplicateCheck}>중복 확인</button>
          </div>
          { !validId && id.length>=1 && (
            <div className='errorMsg'>아이디는 영어 소문자, 숫자가 하나 이상 포함되게 5자 이상으로 작성해주세요. </div>
          )}
        </div>

        {/* 비밀번호1 입력 */}
        <div className="formGroup">
          <div className="formGroupComponent">
            <input className='formInput' onChange={e=>{setPassword1(e.target.value)}} type="password" id="formPassword1" placeholder="비밀번호를 입력해주세요." />
          </div>
          { !validPassword && password1.length>=1 && (
            <div className='errorMsg'>비밀번호는 특수문자, 숫자, 영문자를 하나 이상 포함되게 8자 이상으로 작성해주세요. </div>
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
            <input className='formInput' onChange={e=>{setNickname(e.target.value)}} type="text" id='formNickname' placeholder='닉네임을 입력해주세요'/>
            <button className='duplicationCheckButton' onClick={handleNicknameDuplicateCheck}>중복 확인</button>
          </div>
          { !validNickname && nickname.length>=1 && (
            <div className="errorMsg">닉네임은 1~10자로 작성해주세요.</div>
          )}
        </div>

        {/* 이메일 입력 & 인증번호 요청하기 */}
        <div className="formGroup" id='checkGroup'>
          <div className="formGroupComponent">
            <input className='formInput' onChange={e=>{setEmail(e.target.value)}} type="email" id='formEmail' placeholder='이메일을 입력해주세요.' />
            <button onClick={handleSendCode} className='emailVerificationButton'>인증코드 발송</button>
          </div>
        </div>

        {/* 인증코드 입력 */}
        { sendCode && (
        <div className="formGroup">
          <div className="formGroupComponent">
            <input className='formInput' onChange={e=>{setCode(e.target.value)}} type="text"  placeholder="인증번호 입력" />
            <button onClick={handleEmailCodeCheck} className='verificationBtn'>확인</button>
          </div>
        </div>
        )
        }

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
          구글 로그인 임
        </div>
      </form>
    </div>
  );
}

export default SignupForm;