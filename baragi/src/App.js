/* eslint-disable */
import './App.css';
import React, {useState} from 'react';
import { configureStore } from '@reduxjs/toolkit'
import { Routes, Route, Link, useNavigate, Outlet } from 'react-router-dom'
import NaviBar from './components/ui/NaviBar';

// -------------------PAGES--------------------//
import LoginPage from './pages/LoginPage'
import SignupPage from './pages/SignupPage';
import Recipepage from './pages/RecipePage';
import StreamingPage from './pages/StreamingPage';
import MainPage from './pages/MainPage';
import FindIdPage from './pages/FindIdPage';
import FindPasswordPage from './pages/FindPasswordPage';
// -------------------PAGES-------------------//

function App() {  
  return (
    <div className="App">
      <NaviBar></NaviBar>    
      {/* <FormComponent/> */}
      <br />
      <br></br>

      {/*---------- 모든 Router는 App.js에 적기!!! ------------*/}
      <Routes>
        <Route path='/' element={<MainPage/>}></Route>
        <Route path='/login' element={<LoginPage/>}></Route>
        <Route path='/signup' element={<SignupPage/>}></Route>
        <Route path='/find-id' element={<FindIdPage/>}></Route>
        <Route path='/find-password' element={<FindPasswordPage/>}></Route>
        <Route path='/recipe' element={<Recipepage/>}></Route>
        <Route path='/streaming' element={<StreamingPage/>}></Route>
      </Routes>

    </div>

  );

}
export default App;


