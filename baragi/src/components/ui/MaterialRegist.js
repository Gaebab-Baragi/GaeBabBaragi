import React, { useState } from 'react';
import InputMat from './InputMat'
function MaterialRegist() {
  const [InputMats, setInputMats] = useState([]);
  const handleAddInputMat = ()=>{
    setInputMats([...InputMats,<InputMat key={InputMats.length} />]); 
  }
  return (

    <div container>
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'left', padding: '10px 0'}}>
        <h4>3. 재료 등록</h4>
      </div>

      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '10px 0' ,flexDirection: 'column', gap: '10px' }}>
        {InputMats.map((inputMat, index) => (
          <div key={index}>{inputMat}</div>
        ))}
      </div>
      <button onClick={handleAddInputMat}>+</button>
    </div>
  );
}

export default MaterialRegist;