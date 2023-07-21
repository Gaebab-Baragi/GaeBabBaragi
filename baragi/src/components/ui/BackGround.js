/* eslint-disable */
import './BackGround.css';
import React, {useState} from 'react';
import Container from 'react-bootstrap/Container';

import { Routes, Route, Link, useNavigate, Outlet } from 'react-router-dom'

function BackGround() {
    return (
        <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', background: '#FFEACB' }}>
            <div style={{ textAlign: 'left', marginTop : '5%' , margin : '5% 0' }}>
                <h2>대충 개글 개굴 개굴</h2>
            </div>
            <div style={{ marginLeft: '500px' }}>
                <img src="./image.png" alt="" style={{ width: '500px', height: '500px', objectFit: 'cover', borderRadius: '50%', margin : '5% 0' }}/>
            </div>
        </div>
      );
    }
    export default BackGround;