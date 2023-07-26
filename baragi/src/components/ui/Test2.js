import React from 'react';
import './Test.css';
import InputImage from './InputImage'
import InputInfor from './InputInfor'

function Test2() {
  return (
    <div className="container">
        <h1>레시피 등록</h1>
        <InputImage></InputImage>
        <InputInfor></InputInfor>
        {/* 기본정보입력 */}


    </div>
  );
}

export default Test2;
