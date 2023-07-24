import React, { useState } from 'react';

function InputInfor() {
  const [recipeName, setRecipeName] = useState('');
  const [foodName, setFoodName] = useState('');
  const [recipeIntro, setRecipeIntro] = useState('')
  const handleRecipeNameChange = (e) => {
    setRecipeName(e.target.value);
  };

  const handleFoodNameChange = (e) => {
    setFoodName(e.target.value);
  };

  const handleRecipeIntroChange = (e) => {
    setRecipeIntro(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // 레시피 이름과 음식 이름을 서버로 전송하거나 다른 작업을 수행하는 로직을 추가할 수 있습니다.
    console.log('레시피 이름:', recipeName);
    console.log('음식 이름:', foodName);
  };

  return (
    <>
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'left', padding: '10px 0'}}>
      <h4>2. 기본 정보 입력</h4>
    </div>

      <form onSubmit={handleSubmit}>
      <div style={{ display: 'flex', justifyContent: 'space-between' }}>
        <label htmlFor="recipeName" style={{ marginRight: '10px' }}>레시피 제목:</label>
        <input
          type="text"
          id="recipeName"
          name="recipeName"
          value={recipeName}
          onChange={handleRecipeNameChange}
          placeholder="레시피 제목을 입력하세요."
        />
        </div>
        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
        <label htmlFor="foodName" style={{ marginRight: '10px' }}>음식 이름:</label>
        <input
          type="text"
          id="foodName"
          name="foodName"
          value={foodName}
          onChange={handleFoodNameChange}
          placeholder="음식 이름을 입력하세요."
        />
        </div>
        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
        <label htmlFor="recipeIntro" style={{ marginRight: '10px' }}>레시피 소개:</label>
        <input
          type="text"
          id="recipeIntro"
          name="recipeIntro"
          value={recipeIntro}
          onChange={handleRecipeIntroChange}
          placeholder="레시피를 소개하세요."
        />
        </div>






        

        {/* <button type="submit">제출</button> */}
      </form>
    </>
  );
}

export default InputInfor;
