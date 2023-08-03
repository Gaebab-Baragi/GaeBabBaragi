import React, { useState } from 'react';

function InputCookstep({ step, description, onCookstepChange, onDelete }) {
  const handleCookstepChange = (e) => {
    const newValue = e.target.value;
    onCookstepChange(step, newValue);
  };

  return (
    <>
      <div style={{ display: 'flex', justifyContent: 'space-between', width: '200%', alignItems: 'center' }}>
        <label htmlFor={`Cookstep${step}`}>{`STEP${step}:`}</label>
        <textarea
          rows="5"
          cols="30"
          id={`Cookstep${step}`}
          name={`Cookstep${step}`}
          value={ description }
          onChange={handleCookstepChange}
          placeholder="예)요리방법 돼지고기 소고기 요리해줘"
        />
        <button onClick={onDelete}>-</button>
      </div>
    </>
  );
}

export default InputCookstep;
