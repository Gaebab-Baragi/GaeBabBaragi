import React, { useEffect } from 'react';
import { useNavigate} from 'react-router-dom'
import { useState } from 'react';
import './BasicForm.css'
// import axios from '../../axios/axios';
import axios from 'axios';
import { useDispatch, useSelector } from 'react-redux';
import { loginUser } from '../../redux/userSlice';
import SocialLogin from '../social/SocialLogin';

function LoginForm() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');
  const [msg, setMsg] = useState('');

  const user = useSelector((state) => state.user);
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
      username : id,
      password : password
    };

    axios.post('/api/login', body) 
    .then((res)=>{
      const { accessToken } = res.data;
      axios.defaults.headers.common['Authorization'] =  accessToken;
      console.log("token:" + accessToken);
      if (res.status === 200){
        let data = res.data;
        console.log(data);
        dispatch(loginUser(data))
        navigate('/');
      }
    })
    .catch ((res) => {
        res = res.response;
        console.log(res);
        if (res.status === 452) setMsg("존재하지 않는 아이디입니다.")
        else if (res.status === 453) setMsg("잘못된 비밀번호입니다.")
        else setMsg("알 수 없는 오류")
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
          <a href = "https://www.naver.com/">테스트</a>
          <SocialLogin/>
        </div>
      </form>
    </div>
  );
}

export default LoginForm;