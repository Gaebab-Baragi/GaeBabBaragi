import React, { useState } from 'react';

function InputCookstep({step}) {
    const [Cookstep, setCookstep] = useState('');
    const handleCookstepChange = (e) => {
      setCookstep(e.target.value);
      };

  return (
    <>
    <div style={{ display: 'flex', justifyContent: 'space-between' }}>
      <label htmlFor="Cookstep" style={{ marginRight: '10px' }}>{`STEP${step}:`}</label>
      <input
        type="text"
        id="Cookstep"
        name="Cookstep"
        value={Cookstep}
        onChange={handleCookstepChange}
        placeholder="예)요리방법 돼지고기 소고기 요리해줘"
      />
    </div>
    </>
  );
}

export default InputCookstep;