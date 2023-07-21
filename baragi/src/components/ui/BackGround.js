/* eslint-disable */
import './BackGround.css';
import React, {useState} from 'react';
import Container from 'react-bootstrap/Container';

import { Routes, Route, Link, useNavigate, Outlet } from 'react-router-dom'

function BackGround() {
    return (
        <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', background: '#FFEACB' }}>
            <div style={{ justifyContent: 'center', textAlign: 'center', marginTop : '5%' , margin : '5% 0',width:'50%' }}>
                <h2>"당신의 강아지를 맛있게 웃게 할 비밀 레시피를 공개합니다!"</h2>
            </div>
            <div>
                <img src="./댕댕이.png" alt="" style={{ objectFit: 'cover', margin : '5% 0' }}/>
            </div>
        </div>
      );
    }
    export default BackGround;