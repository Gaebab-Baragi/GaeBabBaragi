/* eslint-disable */
import './NaviBar.css';
import React, {useState} from 'react';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import { Routes, Route, Link, useNavigate, Outlet } from 'react-router-dom'
import LoginPage from '../../pages/LoginPage'

function NaviBar() {
    let navigate = useNavigate();
    return (
        <Navbar expand="lg" className="bg-body-tertiary" bg="dark">
          {/* data-bs-theme="dark" */}
        <Container>
            <img src="/개밥바라기.png" alt="My Image" width="5%" height="1%" />
            <Navbar.Brand href="/" className = 'test1' >개밥바라기</Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav"> 
            <Nav className='me-auto'>
              <Nav.Link href="#home" className='test2'>레시피</Nav.Link>
              <Nav.Link href="#link" className='test2'>스트리밍</Nav.Link>
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
                <Nav.Link href="/pages/LoginPage" eventKey={1} >로그인 / 회원가입</Nav.Link>
              </Nav>
          </Navbar.Collapse>
        </Container>
        </Navbar>
      );
    }
    export default NaviBar;