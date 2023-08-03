import React, { useState } from 'react';
import { useDispatch } from 'react-redux';

import {requestFilteredRecipeList, updateRecipeInfor } from './../../redux/recipeRegisterSlice.js'
import useDidMountEffect from '../../useDidMountEffect.js'

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

  useDidMountEffect(() => {
    // 레시피 이름과 레시피 소개를 상태 변경시마다 dispatch 하고 싶을 때
    dispatch(updateRecipeInfor([recipeName, recipeIntro]));
  }, [recipeName, recipeIntro]);
  

  return (
    <>
      <div style={{ marginTop:'1%', marginBottom :'1%', background: '#0001' }}>
        
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
