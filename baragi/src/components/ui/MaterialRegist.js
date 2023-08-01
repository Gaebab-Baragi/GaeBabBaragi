import React, { useState, useEffect } from 'react';
import InputMat from './InputMat'


function MaterialRegist() {
  const [InputMats, setInputMats] = useState([<InputMat key={0} />]);
  const handleAddInputMat = ()=>{
    setInputMats([...InputMats,<InputMat key={InputMats.length} />]); 
  }
  // useEffect(() => {
  //   // InputCooksteps가 변경될 때마다 실행되는 함수
  //   console.log('InputMats 변경되었습니다:', InputMats);
  //   // 원하는 작업을 수행할 수 있습니다.
  // }, [InputMats]); // inputCooksteps가 변경될 때마다 실행됩니다.

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