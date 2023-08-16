/* eslint-disable */
import './App.css';
import React, {useState} from 'react';
import { Routes, Route, useLocation, useNavigate} from 'react-router-dom'
import NaviBar from './components/ui/navbar/NaviBar';

// -------------------PAGES--------------------//
import LoginPage from './pages/LoginPage'
import SignupPage from './pages/SignupPage';
import RecipeRegisterPage from './pages/RecipeRegisterPage';
import RecipeListPage from './pages/RecipeListPage';
import RecipeUpdatePage from './pages/RecipeUpdatePage';
import StreamingRegisterPage from './pages/StreamingRegisterPage';
import StreamingListPage from './pages/StreamingListPage';
import MyinformationPage from './pages/MyinformationPage';
import MyRecipePage from './pages/MyRecipePage';
import MainPage from './pages/MainPage';
import FindPasswordPage from './pages/FindPasswordPage';
import RecipeDetailPage from './pages/RecipeDetailPage';
import SocialLoginSuccessHandler from './components/social/SocialLoginSuccessHandler'
import LogoutHandler from './components/social/LogoutHandler';
import DuplicateNicknameHandler from './components/social/DuplicateNicknameHandler';
import DuplicateNicknameCheckPage from './pages/DuplicateNicknameCheckPage';
import PasswordModificationPage from './pages/PasswordModificationPage';
import PetListPage from './pages/Pet/PetListPage';
import StreamingLivePage from './streaming/StreamingLivePage';
import ObjectDetectionPage from './pages/ObjectDetectionPage';
import Footer from './components/ui/footer/Footer'
import RecipeWriterPage from './pages/RecipeWriterPage';
// -------------------PAGES-------------------//
import axios from 'axios';
import { useDispatch } from 'react-redux';
import { loginUser, clearUser } from './redux/userSlice';
import { useSelector } from 'react-redux';

import Toast from './components/ui/Toast';

function App() {  
  const dispatch = useDispatch();
  const user = useSelector(state => state.user);
  const navigate = useNavigate();
  const [accToken, setAccToken] = useState('');

  let isFecthedAccessToken = false;
  let subscribers = [];
  
  async function resetTokenAndReattempt(error) {
    try {
      const { response: errorResponse } = error;
  
      const retryOriginalRequest = new Promise((resolve, reject) => {
        addSubscriber(async (accessToken) => {
          try {
            errorResponse.config.headers['Authorization'] = accessToken;
            resolve(axios(errorResponse.config));
          } catch (err) {
            reject(err);
          }
        });
      });

      if (!isFecthedAccessToken){
        isFecthedAccessToken = true;
        const {headers}= await axios.get(process.env.REACT_APP_BASE_URL + "/api/refresh");
        setAccToken(headers.authorization);

        isFecthedAccessToken = false;
        onAccessTokenFetched(headers.authorization);
      }
      return retryOriginalRequest;
    }
    catch (error){
      signOut();
      return Promise.reject(error);
    }
  }

  function addSubscriber(callback) {
    subscribers.push(callback);
  }
  
  function onAccessTokenFetched(accessToken) {
    subscribers.forEach((callback) => callback(accessToken));
    subscribers = [];
  }
  
  function signOut() {
      delete axios.defaults.headers.common.Authorization;//없으면 그냥 없애고 예외처리
      Toast.fire("로그인이 필요한 서비스입니다.", "", "error");
      dispatch(clearUser());
      navigate("/login");
  }

  axios.interceptors.response.use(
    (res) => {
      return res;
    },
    async (err) => {//에러 발생시 
      const { config, response: { status } } = err;
  
      if (config.url === "/api/refresh" || status !== 462 || config.sent) {//refresh 요청을 했는데 에러가 나거나, 462에러가 아니거나, 이미 보낸 상태면
        return Promise.reject(err);//그냥 err리턴
      }
  
      config.sent = true;
      return await resetTokenAndReattempt(err);
    }
  )

  axios.interceptors.request.use((config) => {
    if (accToken) {//저장된 액세스 토큰이 있으면
      config.headers.Authorization =accToken;//헤더에 담음
    }
    else if (config.headers.Authorization) {//헤더에 이미 담겨 있으면
      setAccToken(config.headers.Authorization);//저장함
    }
    return config;
  })
  axios.defaults.withCredentials = true;


  const location = useLocation();
  const noNavAndFooterRoutes = ['/streaming-live'];
  const showNavAndFooter = !noNavAndFooterRoutes.includes(location.pathname);

  return (
    <>
    <div className="App">
    {showNavAndFooter && <NaviBar />}

      {/* <FormComponent/> */}

      {/*---------- 모든 Router는 App.js에 적기!!! ------------*/}
      <Routes>
        <Route path='/' element={<MainPage/>}></Route>
        {/* 회원  */}
        <Route path='/login' element={<LoginPage/>}></Route>
        <Route path='/signup' element={<SignupPage/>}></Route>
        <Route path='/find-password' element={<FindPasswordPage/>}></Route>
        {/* 레시피 */}
        <Route path='/recipe-register/' element={<RecipeRegisterPage/>}></Route>
        <Route path='/recipe-list' element={<RecipeListPage/>}></Route>
        <Route path='/recipe-list/:open' element={<RecipeListPage/>}></Route>
        <Route path='/recipe-detail/:id' element={<RecipeDetailPage/>}></Route>
        <Route path='/recipe-update/:id' element={<RecipeUpdatePage/>}></Route>
        <Route path='/recipe-writer/:id' element={<RecipeWriterPage/>}></Route>
        {/* 스트리밍 */}
        <Route path='/streaming-register/:id' element={<StreamingRegisterPage/>}></Route>
        <Route path='/streaming-list' element={<StreamingListPage/>}></Route>
        <Route path='/streaming-live' element={<StreamingLivePage/>}></Route>
        {/* 내 정보 */}
        <Route path='/myinformation' element={<MyinformationPage/>}></Route>
        <Route path='/myrecipe' element={<MyRecipePage/>}></Route>
        <Route path='/password-modification' element={<PasswordModificationPage/>}></Route>
        {/* 펫  */}
        <Route path='/my-pet-list/:idx' element={<PetListPage/>}></Route>
        <Route path='/my-pet-list' element={<PetListPage/>}></Route>
        
        {/* 로그인 관련 */}
        <Route path='/oauth2/redirect/:token' element={<SocialLoginSuccessHandler/>}></Route>
        <Route path='/logout' element={<LogoutHandler/>}></Route>
        <Route path='/oauth2/signup/:token' element={<DuplicateNicknameHandler></DuplicateNicknameHandler>}></Route>
        <Route path='/oauth2/nickname-check' element={<DuplicateNicknameCheckPage></DuplicateNicknameCheckPage>}></Route>
        
        <Route path="*" element={ <div>없는페이지임</div> } />
      </Routes>

      {showNavAndFooter && <Footer />}
    </div>
    </>
  );

}
export default App;


