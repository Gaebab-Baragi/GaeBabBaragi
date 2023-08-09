/* eslint-disable */
import './App.css';
import React, {useState} from 'react';
import { configureStore } from '@reduxjs/toolkit'
import { Routes, Route, useLocation} from 'react-router-dom'
import NaviBar from './components/ui/navbar/NaviBar';

// -------------------PAGES--------------------//
import LoginPage from './pages/LoginPage'
import SignupPage from './pages/SignupPage';
import RecipeRegisterPage from './pages/RecipeRegisterPage';
import RecipeListPage from './pages/RecipeListPage';
import StreamingRegisterPage from './pages/StreamingRegisterPage';
import StreamingListPage from './pages/StreamingListPage';
import MyinformationPage from './pages/MyinformationPage';
import MyRecipePage from './pages/MyRecipePage';
import MainPage from './pages/MainPage';
import FindIdPage from './pages/FindIdPage';
import FindPasswordPage from './pages/FindPasswordPage';
import RecipeDetailPage from './pages/RecipeDetailPage';
import SocialLoginSuccessHandler from './components/social/SocialLoginSuccessHandler'
import LogoutHandler from './components/social/LogoutHandler';
import DuplicateNickname from './components/social/DuplicateNickname';

import PetRegisterPage from './pages/Pet/PetRegisterPage';
import PetListPage from './pages/Pet/PetListPage';
import StreamingLivePage from './streaming/StreamingLivePage';
import ObjectDetectionPage from './pages/ObjectDetectionPage';
import Footer from './components/ui/Footer'
// -------------------PAGES-------------------//
import axios from 'axios';
import { useSelector, useDispatch } from 'react-redux';
import { loginUser } from './redux/userSlice';

function App() {  

  const user = useSelector((state) => state.user);
  const dispatch = useDispatch();
  axios.interceptors.response.use(
    (res) => {
      if (res.headers['authorization']) {
        console.log(res.headers.authorization);
        axios.defaults.headers.common['Authorization'] = "Bearer " + res.headers['authorization']
        dispatch(loginUser({isLogin : true}))
      }
      
      return res;
    }
    )
    
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
        <Route path='/find-id' element={<FindIdPage/>}></Route>
        <Route path='/find-password' element={<FindPasswordPage/>}></Route>
        {/* 레시피 */}
        <Route path='/recipe-register/' element={<RecipeRegisterPage/>}></Route>
        <Route path='/recipe-list' element={<RecipeListPage/>}></Route>
        <Route path='/recipe-detail/:id' element={<RecipeDetailPage/>}></Route>
        {/* 스트리밍 */}
        <Route path='/streaming-register/:id' element={<StreamingRegisterPage/>}></Route>
        <Route path='/streaming-list' element={<StreamingListPage/>}></Route>
        <Route path='/streaming-live' element={<StreamingLivePage/>}></Route>
        {/* 내 정보 */}
        <Route path='/myinformation' element={<MyinformationPage/>}></Route>
        <Route path='/myrecipe' element={<MyRecipePage/>}></Route>
        {/* 펫  */}
        <Route path='/my-pet-list/:idx' element={<PetListPage/>}></Route>
        <Route path='/my-pet-list' element={<PetListPage/>}></Route>
        
        {/*-----------------------로그인 관련-------------------------------*/}
        <Route path='/oauth2/redirect/:token' element={<SocialLoginSuccessHandler/>}></Route>
        <Route path='/logout' element={<LogoutHandler/>}></Route>
        <Route path='/oauth2/signup' element={<DuplicateNickname></DuplicateNickname>}></Route>
        
        <Route path="*" element={ <div>없는페이지임</div> } />
        <Route path='/object-detect' element={<ObjectDetectionPage></ObjectDetectionPage>}></Route>
      </Routes>

      {showNavAndFooter && <Footer />}
    </div>
    </>
  );

}
export default App;


