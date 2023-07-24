import React from 'react';
import './Test.css';
import InputImage from './InputImage'
import InputInfor from './InputInfor'
import MaterialRegist from './MaterialRegist'
import CookStep from './CookStep'
function Test2() {
  return (
    <>
    
    <div>
        <h1>레시피 등록</h1>
        <InputImage></InputImage>
        <InputInfor></InputInfor>
        <MaterialRegist></MaterialRegist>
        <CookStep></CookStep>
    </div>
    </>
  );
}

export default Test2;
