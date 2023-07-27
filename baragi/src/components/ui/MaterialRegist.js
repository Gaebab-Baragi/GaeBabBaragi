import React, { useState } from 'react';
import InputMat from './InputMat'




function MaterialRegist() {
  const [InputMats, setInputMats] = useState([<InputMat key={0} />]);
  const handleAddInputMat = ()=>{
    setInputMats([...InputMats,<InputMat key={InputMats.length} />]); 
  }
  return (
    <>
    <div style={{ display: 'flex', padding: '1%'}}>
        <h4 style={{textAlign:'left', marginLeft:'2%', marginRight : '15%'}}>3. 재료 등록</h4>
    </div>
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '10px 0' ,flexDirection: 'column', gap: '10px' }}>
        {InputMats.map((inputMat, index) => (
        <div key={index}>{inputMat}</div>
        ))}

      </div>
              
      <button onClick={handleAddInputMat}>+</button>
    </>
  );
}

export default MaterialRegist;