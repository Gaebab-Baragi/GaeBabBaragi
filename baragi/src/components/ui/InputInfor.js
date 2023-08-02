import React, { useState, useEffect } from 'react';
import { useDispatch } from 'react-redux';
import {setRecipeName2, setRecipeInfor } from './../../redux/recipeRegisterSlice.js'

function InputInfor() {
  const dispatch = useDispatch();

  const [recipeName, setRecipeName] = useState('');
  const [recipeIntro, setRecipeIntro] = useState('');

  const handleRecipeNameChange = (e) => {
    setRecipeName(e.target.value);
  };

  const handleRecipeIntroChange = (e) => {
    setRecipeIntro(e.target.value);
  };

  useEffect(() => {
    // 레시피 이름과 레시피 소개를 상태 변경시마다 dispatch 하고 싶을 때
    dispatch(setRecipeName2(recipeName));
    dispatch(setRecipeInfor(recipeIntro));
    // console.log('변경되었습니다:', [recipeName,recipeIntro]);
  }, [recipeName, recipeIntro]);

  return (
    <>
      <div style={{ padding: '1%' }}>
        <h4 style={{ textAlign: 'left', marginLeft: '2%', marginRight: '15%' }}>2. 기본 정보 입력</h4>
        <form style={{ marginLeft: '2%', marginRight: '15%', padding: '1%' }}>
          <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '1%' }}>
            <label htmlFor="recipeName" style={{ width: '15%' }}>레시피 제목:</label>
            <input
              type="text"
              id="recipeName"
              name="recipeName"
              value={recipeName}
              onChange={handleRecipeNameChange}
              placeholder="레시피 제목을 입력하세요."
            />
          </div>
          <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom: '1%' }}>
            <label htmlFor="recipeIntro" style={{ width: '15%' }}>레시피 소개:</label>
            <input
              type="text"
              id="recipeIntro"
              name="recipeIntro"
              value={recipeIntro}
              onChange={handleRecipeIntroChange}
              placeholder="레시피를 소개하세요."
            />
          </div>
        </form>
      </div>
    </>
  );
}

export default InputInfor;
