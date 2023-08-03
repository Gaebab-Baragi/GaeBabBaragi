import React, { useState, useEffect } from 'react';
import InputMat from './InputMat'


function MaterialRegist() {
  const [inputMats, setInputMats] = useState([{name:'', amount:''}]);
  
  const handleAddInputMat = () => {
    setInputMats([...inputMats, { name: '', amount: '' }]);
  };
  const handleDeleteInputMat = (index) => {
    setInputMats((prevInputMats) => {
      const updatedMats = [...prevInputMats];
      updatedMats.splice(index, 1);
      return updatedMats;
    });
  };
  const handleIngredientsChange = (step, field, value) => {
    setInputMats((prevInputMats) => {
      const updatedMats = [...prevInputMats];
      updatedMats[step] = { ...updatedMats[step], [field]: value };
      return updatedMats;
    });
  };
  useEffect(() => {
    // inputMats 변경될 때마다 실행되는 함수
    console.log('inputMats 변경되었습니다:', inputMats);
    // 원하는 작업을 수행할 수 있습니다.
  }, [inputMats]); // inputMats 변경될 때마다 실행됩니다.

  return (
    <>
    <div style={{ display: 'flex', padding: '1%'}}>
        <h4 style={{textAlign:'left', marginLeft:'2%', marginRight : '15%'}}>3. 재료 등록</h4>
    </div>
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '10px 0' ,flexDirection: 'column', gap: '10px' }}>
      {inputMats.map((inputMat, index) => (
        <div key={index}>
          <InputMat step={index} matName={inputMat.name} matAmount={inputMat.amount} ingredientsChange={handleIngredientsChange} />
          <button onClick={() => handleDeleteInputMat(index)}>-</button>
          </div>
        ))}

      </div>
      <button onClick={handleAddInputMat}>+</button>
    </>
  );
}

export default MaterialRegist;