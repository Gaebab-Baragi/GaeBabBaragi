import React, { useEffect } from 'react';
import { redirect, useNavigate} from 'react-router-dom'
import { useState } from 'react';
import './BasicForm.css'
import axios from 'axios';
import { useDispatch } from 'react-redux';
import { loginUser } from '../../redux/userSlice';


function LoginForm() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  let [id, setId] = useState('');
  let [password, setPassword] = useState('');
  let [msg, setMsg] = useState('')

  // 오류 메세지 alert
  useEffect(()=>{
    if (msg) {
      alert(msg)
    }
  },[msg])


  // ----------- 로그인 기능 구현-------------------//
  const handleLogin = (e) => {
    // Reload 막기
    e.preventDefault();
    // 빈 값 처리
    if (!id) {
      return alert('아이디를 입력하세요.')
    } else if (!password) {
      return alert('비밀번호를 입력하세요')
    }
    // axios 요청 보내기
    let body = {
      id,
      password
    };

    axios.post('/member/login', body)
    .then((res)=>{
      console.log(res.data);
      switch (res.data.code) {
        case 200:
          console.log("로그인");
          dispatch(loginUser(res.data.userInfo));
          break;
        case 400:
          setMsg("아이디, 비밀번호가 비어있습니다.");
          break;
        case 401:
          setMsg("존재하지 않는 아이디입니다.");
          break;
        case 402:
          setMsg("비밀번호가 틀렸습니다.");
          break;
        default:
          break;
      }
    })

  }
  // ---------------------------------------------//


  return (
    <div className="formContainer">
      <form onSubmit={handleLogin} className="form">
        <div className="formTitle">로그인</div>

        {/* 아이디 입력  */}
        <div className="formGroup">
          <input className='formInput' onChange={e=>{setId(e.target.value)}} type="id" id="formBasicEmail" placeholder="아이디를 입력해주세요." />
        </div>

        {/* 비밀번호 입력 */}
        <div className="formGroup">
          <input className='formInput' onChange={e=>{setPassword(e.target.value)}} type="password" id="formBasicPassword" placeholder="비밀번호를 입력해주세요." />
        </div>

        {/* 아이디 찾기 / 비밀번호 찾기 / 회원가입 */}
        <div className='formGroup' id='navigateNewPage'>
          <span onClick={()=>{navigate('/find-id')}}>아이디 찾기</span>|<span onClick={()=>{navigate('/find-password')}}>비밀번호 찾기</span> | <span onClick={()=>navigate('/signup')}>회원가입</span>
        </div>

        {/* Login 버튼 */}
        <button className="submitButton" type="submit">
          LOGIN
        </button>

        {/* OR  */}
        <hr />

        {/* 구글 로그인 */}
        <div className="formGroup">
          구글 로그인 임
        </div>
      </form>
    </div>
  );
}

export default LoginForm;