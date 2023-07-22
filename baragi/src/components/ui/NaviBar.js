/* eslint-disable */
import './NaviBar.css';
import React, {useState} from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { Routes, Route, Link, useNavigate, Outlet } from 'react-router-dom'


function NaviBar() {
    let navigate = useNavigate();
    return (
      <>
        <Navbar expand="lg">
          {/* data-bs-theme="dark" */}
            <img src="/개밥바라기.png" alt="My Image" width="5%" height="1%" />
            <Navbar.Brand href="/" className = 'test1' >개밥바라기</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav"> 
            <Nav className='mx-auto'>
              <Nav.Link onClick={()=>{navigate('/recipe')}} className='test2'>레시피</Nav.Link>
              <Nav.Link onClick={()=>{navigate('/streaming')}}  className='test2'>스트리밍</Nav.Link>
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
                <Nav.Link onClick={()=>{navigate('/login')}} eventKey={3} className='test2' >로그인/</Nav.Link>
                <Nav.Link onClick={()=>{navigate('/signup')}} eventKey={4} className='test2' >회원가입</Nav.Link>
              </Nav>
          </Navbar.Collapse>
        </Navbar>
{/* 주석인데 여기 네비바에 로그인 여부에 따라 프로필 보여질지 로그인/회원가입 보여질지 선택  */}

        
        </>
      );
    }
    export default NaviBar;