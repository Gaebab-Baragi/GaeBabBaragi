/* eslint-disable */
import './App.css';
import CardList from './components/list/CardList';
import CardCarousel from './components/list/CardCarousel';
import React, {useState} from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import Button from 'react-bootstrap/Button';
import { configureStore } from '@reduxjs/toolkit'
import { Routes, Route, Link, useNavigate, Outlet } from 'react-router-dom'
import NaviBar from './components/ui/NaviBar';
import BackGround from './components/ui/BackGround';
import LoginPage from './pages/LoginPage'
import FormComponent from './components/ui/LoginForm';
import LoginForm from './components/ui/LoginForm';
import MainPage from './pages/MainPage';
import Test2 from './components/ui/InputImage'

function App() {  
  return (
    <>
    <div className="App">
      <NaviBar></NaviBar>
      {/* <FormComponent/> */}
      {/* 카드 컴포넌트 만들고 / 데이터 받으면 거기서넣어주기/? */}
        <Routes>
        <Route path='' element={<MainPage/>}></Route>
        </Routes>
    </div>
    </>
  );

}
export default App;



