/* eslint-disable */
import './NaviBar.css';
import React, {useState} from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { Routes, Route, Link, useNavigate, Outlet } from 'react-router-dom'
import { useSelector } from 'react-redux';
import { NavbarBrand } from 'react-bootstrap';


function NaviBar() {
    let navigate = useNavigate();
    let user = useSelector((state) => state.user);
    return (
      <>
        <Navbar sticky="top" expand="lg" bg='white'>
          <Container>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Brand href="/" className="d-inline-block align-top">
              <img
                alt="개밥바라기 로고"
                src="/image/개밥바라기.png"
                width="60"
                height="60"
              />{' '}
              <span className='navbar-title'>개밥바라기</span>
            </Navbar.Brand>
          </Container>
        </Navbar>
        <Navbar expand="lg" bg='white'>
          <Container>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Brand>
              <img alt="개밥바라기 logo" src="/image/개밥바라기.png" width="8%" height="8%" className='d-inline-block align-top'/>{' '}
              <span className='title'>개밥바라기</span>
            </Navbar.Brand>
            <Navbar.Collapse id="basic-navbar-nav">
            <Nav className='me-auto my-2 my-lg-0'>
              <Nav.Link onClick={()=>{navigate('/recipe-list')}} className='list1'>레시피</Nav.Link>
              <Nav.Link onClick={()=>{navigate('/streaming-list')}}  className='list1'>스트리밍</Nav.Link>
            </Nav>
                {/* 로그인 X인경우 */}
            {
              !user.isLogin ? ( 
                <Nav>
                  <Nav.Link onClick={()=>{navigate('/login')}} eventKey={3} className='list2' >로그인</Nav.Link>
                  <Nav.Link onClick={()=>{navigate('/signup')}} eventKey={4} className='list2' >회원가입</Nav.Link>
                </Nav>
              )
              : (
              <Nav>
                <NavDropdown title="내 프로필" id="basic-nav-dropdown" className='list2'>
                  <NavDropdown.Item onClick={()=>{navigate('/myinformation')}} eventKey={5}>내 정보변경</NavDropdown.Item>
                  <NavDropdown.Item onClick={()=>{navigate('/mypetregister')}} eventKey={6}>반려견 등록</NavDropdown.Item>
                  <NavDropdown.Item onClick={()=>{navigate('/myrecipe')}} eventKey={7}>나의/내가 찜한 레시피</NavDropdown.Item>
                  <NavDropdown.Divider />
                  <NavDropdown.Item onClick={()=>{navigate('/logout')}}> 로그아웃 </NavDropdown.Item>
                </NavDropdown>
              </Nav>
              )
            }
          </Navbar.Collapse>
          </Container>
        </Navbar>
{/* 주석인데 여기 네비바에 로그인 여부에 따라 프로필 보여질지 로그인/회원가입 보여질지 선택  */}

        
        </>
      );
    }
    export default NaviBar;