import React, { useState, useEffect } from 'react';
import InputMat from './InputMat'
import { useDispatch } from 'react-redux';
import {updateRecipeMaterial} from '../../redux/recipeRegisterSlice'
import useDidMountEffect from '../../useDidMountEffect'
function MaterialRegist() {
  const dispatch = useDispatch();
  const [inputMats, setInputMats] = useState([{ingredientName:'', amount:''}]);
  
  const handleAddInputMat = () => {
    setInputMats([...inputMats, { ingredientName: '', amount: '' }]);
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
  useDidMountEffect(() => {
    // 레시피 이름과 레시피 소개를 상태 변경시마다 dispatch 하고 싶을 때
    // console.log('use에서' , inputMats)

    dispatch(updateRecipeMaterial(inputMats));
  },[inputMats])




  //   useEffect(() => {
  //   // inputMats 변경될 때마다 실행되는 함수
  //   console.log('inputMats use변경되었습니다:', inputMats);
  //   // 원하는 작업을 수행할 수 있습니다.
  // }, [inputMats]); // inputMats 변경될 때마다 실행됩니다.

  return (
    <>
    <div style={{ display: 'flex', padding: '1%'}}>
        <h4 style={{textAlign:'left', marginLeft:'2%', marginRight : '15%'}}>3. 재료 등록</h4>
    </div>
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '10px 0' ,flexDirection: 'column', gap: '10px' }}>
      {inputMats.map((inputMat, index) => (
        <div key={index}>
          <InputMat step={index} matName={inputMat.ingredientName} matAmount={inputMat.amount} ingredientsChange={handleIngredientsChange} />
          <button onClick={() => handleDeleteInputMat(index)}>-</button>
          </div>
        ))}

      </div>
      <button onClick={handleAddInputMat}>+</button>
    </>
  );
}

export default MaterialRegist;