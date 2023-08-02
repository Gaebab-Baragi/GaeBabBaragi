/* eslint-disable */
import './App.css';
import React, {useState} from 'react';
import { configureStore } from '@reduxjs/toolkit'
import { Routes, Route, Link, useNavigate, Outlet } from 'react-router-dom'
import NaviBar from './components/ui/NaviBar';

// -------------------PAGES--------------------//
import LoginPage from './pages/LoginPage'
import SignupPage from './pages/SignupPage';
import RecipeRegisterPage from './pages/RecipeRegisterPage';
import RecipeListPage from './pages/RecipeListPage';
import StreamingRegisterPage from './pages/StreamingRegisterPage';
import StreamingListPage from './pages/StreamingListPage';
import MyinformationPage from './pages/MyinformationPage';
import MyPetRegisterPage from './pages/MyPetRegisterPage';
import MyRecipePage from './pages/MyRecipePage';
import MainPage from './pages/MainPage';
import FindIdPage from './pages/FindIdPage';
import FindPasswordPage from './pages/FindPasswordPage';
import RecipeDetailPage from './pages/RecipeDetailPage';
import SocialLoginSuccessHandler from './components/social/SocialLoginSuccessHandler'
import LogoutHandler from './components/social/LogoutHandler';

// -------------------PAGES-------------------//


function App() {  
  return (
    <>
    <div className="App">
      <NaviBar></NaviBar>

      {/* <FormComponent/> */}

      {/*---------- 모든 Router는 App.js에 적기!!! ------------*/}
      <Routes>
        <Route path='/' element={<MainPage/>}></Route>
        <Route path='/login' element={<LoginPage/>}></Route>
        <Route path='/signup' element={<SignupPage/>}></Route>
        <Route path='/find-id' element={<FindIdPage/>}></Route>
        <Route path='/find-password' element={<FindPasswordPage/>}></Route>
        <Route path='/recipe-regist' element={<RecipeRegisterPage/>}></Route>
        <Route path='/recipe-list' element={<RecipeListPage/>}></Route>
        <Route path='/streaming-register' element={<StreamingRegisterPage/>}></Route>
        <Route path='/streaming-list' element={<StreamingListPage/>}></Route>
        <Route path='/recipe-detail' element={<RecipeDetailPage/>}></Route>
        <Route path='/oauth2/redirect/:token' element={<SocialLoginSuccessHandler/>}></Route>
        <Route path='/myinformation' element={<MyinformationPage/>}></Route>
        <Route path='/mypetregister' element={<MyPetRegisterPage/>}></Route>
        <Route path='/myrecipe' element={<MyRecipePage/>}></Route>
        <Route path="*" element={ <div>없는페이지임</div> } />
      </Routes>

    </div>
    </>
  );

}
export default App;


