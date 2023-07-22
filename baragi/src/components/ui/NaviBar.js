/* eslint-disable */
import './NaviBar.css';
import React, {useState} from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { Routes, Route, Link, useNavigate, Outlet } from 'react-router-dom'
import LoginPage from '../../pages/LoginPage'
import SignupPage from '../../pages/SignupPage'
import RecipePage from '../../pages/RecipePage'
import StreamingPage from '../../pages/StreamingPage'
import LoginForm from './LoginForm'


function NaviBar() {
    let navigate = useNavigate();
    return (
      <>
        <Navbar sticky="top" expand="lg" bg='dark'>
          <Container >
          {/* data-bs-theme="dark" */}
            <img src="/개밥바라기.png" alt="My Image" width="5%" height="1%" />
            <Navbar.Brand href="/" className = 'test1' >개밥바라기</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav"> 
            <Nav className='mx-auto'>
              <Nav.Link onClick={()=>{navigate('/pages/RecipePage')}} className='test2'>레시피</Nav.Link>
              <Nav.Link onClick={()=>{navigate('/pages/StreamingPage')}}  className='test2'>스트리밍</Nav.Link>
              <NavDropdown title="커뮤니티" id="basic-nav-dropdown" className='test2'>
                <NavDropdown.Item href="#action/3.1">Action</NavDropdown.Item>
                <NavDropdown.Item href="#action/3.2">
                  Another action
                </NavDropdown.Item>
                <NavDropdown.Item href="#action/3.3">Something</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item href="#action/3.4">
                  Separated link
                </NavDropdown.Item>
              </NavDropdown>
              </Nav>
              <Nav>
                <Nav.Link onClick={()=>{navigate('/pages/LoginPage')}} eventKey={3} className='test2' >로그인/</Nav.Link>
                <Nav.Link onClick={()=>{navigate('/pages/SignupPage')}} eventKey={4} className='test2' >회원가입</Nav.Link>
              </Nav>
          </Navbar.Collapse>
          </Container>
        </Navbar>
{/* 주석인데 여기 네비바에 로그인 여부에 따라 프로필 보여질지 로그인/회원가입 보여질지 선택  */}

        <Routes>
          <Route path='/' element={<></>}></Route>
          <Route path='/pages/LoginPage' element={<LoginForm/>}></Route>
          <Route path='/pages/SignupPage' element={<SignupPage/>}></Route>
          <Route path='/pages/RecipePage' element={<RecipePage/>}></Route>
          <Route path='/pages/StreamingPage' element={<StreamingPage/>}></Route>
          
        </Routes>
        </>
      );
    }
    export default NaviBar;