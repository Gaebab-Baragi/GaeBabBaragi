import React, { useState, useEffect } from 'react';

function InputCookstep({ step }) {
  const [cookstepValue, setCookstepValue] = useState('');

  const handleCookstepChange = (e) => {
    setCookstepValue(e.target.value);
  };
  
  useEffect(() => {
    console.log(step, cookstepValue);
  }, [step, cookstepValue]);
  return (
    <>
      <div style={{ display: 'flex', justifyContent: 'space-between', width: '200%', alignItems: 'center' }}>
        <label htmlFor={`Cookstep${step}`}>{`STEP${step}:`}</label>
        <textarea
          rows="5"
          cols="30"
          id={`Cookstep${step}`}
          name={`Cookstep${step}`}
          value={cookstepValue}
          onChange={handleCookstepChange}
          placeholder="예)요리방법 돼지고기 소고기 요리해줘"
        />
      </div>
    </>
  );
}

export default InputCookstep;