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
      
        <Navbar sticky="top" expand="lg" style={{backgroundColor: 'white'}}>
          <Container >
          {/* data-bs-theme="dark" */}
            <img src="/개밥바라기.png" alt="My Image" width="5%" height="1%" />
            <Navbar.Brand href="/" className = 'title' >개밥바라기</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
            <Nav className='mx-auto'>
              <Nav.Link onClick={()=>{navigate('/recipe-list')}} className='list1'>레시피</Nav.Link>
              <Nav.Link onClick={()=>{navigate('/streaming')}}  className='list1'>스트리밍</Nav.Link>
              </Nav>
              <Nav>

                {/* 로그인 X인경우 */}
                <Nav.Link onClick={()=>{navigate('/login')}} eventKey={3} className='list2' >로그인</Nav.Link>
                <Nav.Link onClick={()=>{navigate('/signup')}} eventKey={4} className='list2' >회원가입</Nav.Link>
                {/* 로그인 0인 경우 */}
                <NavDropdown title="내 프로필" id="basic-nav-dropdown" className='list2'>
                <NavDropdown.Item onClick={()=>{navigate('/myinformation')}} eventKey={5}>내 정보변경</NavDropdown.Item>
                <NavDropdown.Item onClick={()=>{navigate('/mypetregister')}} eventKey={6}>
                  반려견 등록
                </NavDropdown.Item>
                <NavDropdown.Item onClick={()=>{navigate('/myrecipe')}} eventKey={7}>나의/내가 찜한 레시피</NavDropdown.Item>
                <NavDropdown.Divider />
                <NavDropdown.Item onClick={()=>{navigate('')}}>
                  로그아웃
                </NavDropdown.Item>
              </NavDropdown>
              </Nav>
          </Navbar.Collapse>
          </Container>
        </Navbar>
{/* 주석인데 여기 네비바에 로그인 여부에 따라 프로필 보여질지 로그인/회원가입 보여질지 선택  */}

        
        </>
      );
    }
    export default NaviBar;