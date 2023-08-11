import React, { useEffect } from 'react';
import { useNavigate} from 'react-router-dom'
import { useState } from 'react';
import './css/BasicForm.css'
import { useDispatch, useSelector } from 'react-redux';
import { loginUser } from '../../redux/userSlice';
import SocialLogin from '../social/SocialLogin';
import axios from 'axios';
import Toast from '../ui/Toast';

function LoginForm() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');
  const [msg, setMsg] = useState('');

  // ----------- 로그인 기능 구현-------------------//
  const handleLogin = (e) => {
    // Reload 막기
    e.preventDefault();
    // 빈 값 처리
    if (!id) {
      return Toast.fire("아이디를 입력해주세요", "", "warning")
    } else if (!password) {
      return Toast.fire("비밀번호를 입력해주세요", "", "warning")
    }
    // axios 요청 보내기
    let body = {
      username : id,
      password : password
    };

    axios.post(process.env.REACT_APP_BASE_URL +'/api/login', body) 
    .then((res)=>{
      if (res.status === 200){
        const accessToken = res.headers['authorization'];
        axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`; 
        const data = res.data;
        dispatch(loginUser(data))
        navigate('/');
      }
    })
    .catch ((res) => {
        res = res.response;
        Toast.fire("아이디나 비밀번호를 확인해주세요", "", "error");
      }
    )
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
          <span onClick={()=>{navigate('/find-password')}}>비밀번호 찾기</span> | <span onClick={()=>navigate('/signup')}>회원가입</span>
        </div>

        {/* Login 버튼 */}
        <button className="submitButton" type="submit">
          LOGIN
        </button>

        {/* OR  */}
        <hr />

        {/* 구글 로그인 */}
        <div className="formGroup">
          <SocialLogin/>
        </div>
      </form>
    </div>
  );
}

export default LoginForm;
