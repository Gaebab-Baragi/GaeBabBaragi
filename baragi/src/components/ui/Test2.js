import React from 'react';
import './Test.css';
import InputImage from './InputImage'
import InputInfor from './InputInfor'
import MaterialRegist from './MaterialRegist'
import CookStep from './CookStep'
function Test2() {
  return (
    <>
    
    <div style={{ marginLeft:'10%' , marginRight : '10%' , marginTop : '0.5%' , marginBottom : '10%'}}>
        <h1 style={{ textAlign : 'left' }}>레시피 등록</h1>
        <InputImage></InputImage>
        <InputInfor></InputInfor>
        <MaterialRegist></MaterialRegist>
        <CookStep></CookStep>
    </div>
    </>
  );
}

export default Test2;
