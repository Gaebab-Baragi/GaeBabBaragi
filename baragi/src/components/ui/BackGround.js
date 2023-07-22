/* eslint-disable */
import './BackGround.css';
import React, {useState} from 'react';
import Container from 'react-bootstrap/Container';

import { Routes, Route, Link, useNavigate, Outlet } from 'react-router-dom'

function BackGround() {
    return (
        <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', background: '#FFEACB' }}>
            <div style={{ width: '40%' }}>
                <h2 style={{ textAlign: 'center' }}>"당신의 강아지를 맛있게 웃게 할 비밀 레시피를 공개합니다!"</h2>
            </div>
            <div className='imgtest' style={{ width: '50%', display: 'flex', justifyContent: 'center' }}>
                <img src="./댕댕이.png" alt="" style={{ width: '80%', height: 'auto' }} />
            </div>
        </div>

      );
    }
    export default BackGround;