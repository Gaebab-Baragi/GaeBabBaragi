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

function App() {
  
  return (
    <div className="App">

      <NaviBar></NaviBar>    
      <BackGround></BackGround>
      <h2>추천레시피</h2>
      <CardCarousel/>
      <br />
      <br></br>
      {/* 카드 컴포넌트 만들고 / 데이터 받으면 거기서넣어주기/? */}

    </div>
  );
}
export default App;



