import React, { useState } from 'react';
import '../../pages/css/StreamingRegisterPage.css'


function InputMat({ step, matName, matAmount, ingredientsChange }) {
  const handleMatnameChange = (e) => {
    const newMatName = e.target.value;
    ingredientsChange(step, 'ingredientName', newMatName);
  };

  const handleMatamountChange = (e) => {
    const newMatAmount = e.target.value;
    ingredientsChange(step, 'amount', newMatAmount);
  };

  return (

      <div style={{ borderRadius: '5px', width : '90%', backgroundColor: '#FFEACB', display:'', alignItems: 'center',  borderRadius: '10px',justifyContent: '' }}>
        {/* <div style={{ margin : '5% 5% 5% 5%'}}> */}
          <input 
            className='roomName'
            type="text"
            id={`Matname${step}`}
            name={`Matname${step}`}
            value={matName}
            onChange={handleMatnameChange}
            placeholder="재료를 입력하세요"
            style={{width:'45%', textAlign: 'center', margin : '2%'}}
          />
        {/* </div>
        <div style={{ width : '150%' , margin: '5% -5% 5% 5%' }}> */}
          <input
            className='roomName'
            type="text"
            id={`matAmount${step}`}
            name={`matAmount${step}`}
            value={matAmount}
            onChange={handleMatamountChange}
            placeholder="양을 입력하세요"
            style={{ width:'45%', textAlign: 'center', margin:'2%'}}
          />
        {/* </div> */}
      </div>

  );
}

export default InputMat;
