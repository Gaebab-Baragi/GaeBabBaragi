import React, { useState } from 'react';
import InputImage from './InputImage';
import '../../pages/PetRegisterPage.css'


function InputCookstep({ step, description, onCookstepChange, onDelete }) {
  const handleCookstepChange = (e) => {
    const newValue = e.target.value;
    onCookstepChange(step, newValue);
  };

  return (
    <>
      <div style={{ display: 'flex', marginLeft : '2%',  marginRgiht:'2%' }}>
        <label style={{ width : '15%' }} htmlFor={`Cookstep${step}`}>{`STEP${step}:`}</label>
        <textarea
          rows="5"
          cols="30"
          id={`Cookstep${step}`}
          name={`Cookstep${step}`}
          value={ description }
          onChange={handleCookstepChange}
          placeholder="예)요리방법 돼지고기 소고기 요리해줘"
          style = {{ flex : 1, marginRight : '3%' }}
        />
        <div style={{ width: '20%', height :'120%'}}>
          <InputImage></InputImage>
        </div>
      </div>
      <button onClick={onDelete}>-</button>
    </>
  );
}

export default InputCookstep;
