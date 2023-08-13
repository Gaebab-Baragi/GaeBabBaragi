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
import { Menu } from '@mui/icons-material';


function NaviBar() {
    let navigate = useNavigate();
    let user = useSelector((state) => state.user);

    return (
      <>
        <Navbar collapseOnSelect sticky="top" expand="lg" bg='white'>
          <Container>
            <Navbar.Brand href='/' style={{cursor:'pointer'}}>
              <img
                alt="개밥바라기 로고"
                src="/image/개밥바라기.png"
                className='d-inline-block align-middle navbar-logo'
              />{' '}
              <span className='d-inline-block align-middle navbar-title'>개밥바라기</span>
            </Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav">
              <Menu/>
            </Navbar.Toggle>
            <Navbar.Collapse id="basic-navbar-nav">
              <Nav className='me-auto my-2 my-lg-0'>
                <Nav.Link as={Link} to='/recipe-list' href='/recipe-list'>레시피</Nav.Link>
                <Nav.Link as={Link} to='/streaming-list' href='/streaming-list'>스트리밍</Nav.Link>
                <Nav.Link as={Link} to='/' href='/'>커뮤니티</Nav.Link>
              </Nav>
              {
              !user.isLogin ? ( 
                <Nav> {/* 로그인 X */}
                  <Nav.Link as={Link} to='/login' href='/login' eventKey={3} className='navbar-nav2-link-list' >로그인</Nav.Link>
                  <Nav.Link as={Link} to='/signup' href='/signup' eventKey={4} className='navbar-nav2-link-list' >회원가입</Nav.Link>
                </Nav>
              )
              : (
              <Nav> {/* 로그인 O */}
                <NavDropdown title="내 프로필" id="basic-nav-dropdown">
                  <NavDropdown.Item as={Link} to='/myinformation' href='/myinformation' eventKey={5} className='navbar-nav2-dropdown'>내 정보변경</NavDropdown.Item>
                  <NavDropdown.Item as={Link} to='/my-pet-list/0' href='/my-pet-list/0' eventKey={6} className='navbar-nav2-dropdown'>반려견 관리</NavDropdown.Item>
                  <NavDropdown.Item as={Link} to='/myrecipe' href='/myrecipe' eventKey={7} className='navbar-nav2-dropdown'>마이페이지</NavDropdown.Item>
                  <NavDropdown.Divider />
                  <NavDropdown.Item as={Link} to='/logout' href='/logout' className='navbar-nav2-dropdown'> 로그아웃 </NavDropdown.Item>
                </NavDropdown>
              </Nav>
              )
              }
            </Navbar.Collapse>
          </Container>
        </Navbar>

        
        </>
      );
    }
    export default NaviBar;
