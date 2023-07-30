import React, { useState } from 'react';

function InputCookstep({step}) {
    const [Cookstep, setCookstep] = useState('');
    const handleCookstepChange = (e) => {
      setCookstep(e.target.value);
      };
  return (
    <>
    <div style={{ display: 'flex', justifyContent: 'space-between', width : '200%' , alignItems: 'center'}}>
      <label htmlFor="Cookstep" style={{ }}>{`STEP${step}:`}</label>
      <textarea
        // rows="5" // 여러 줄 입력 필드의 높이를 5줄로 설정  
        // cols="30" // 여러 줄 입력 필드의 너비를 30글자로 설정

        id="Cookstep"
        name="Cookstep"
        value={Cookstep}
        onChange={handleCookstepChange}
        placeholder="예)요리방법 돼지고기 소고기 요리해줘"
      />
      {/* <input
        type="text"
        id="Cookstep"
        name="Cookstep"
        value={Cookstep}
        onChange={handleCookstepChange}
        placeholder="예)요리방법 돼지고기 소고기 요리해줘"
        style={{ width: '100%', wordWrap: 'break-word' }}
      /> */}
    </div>
    </>
  );
}

export default InputCookstep;