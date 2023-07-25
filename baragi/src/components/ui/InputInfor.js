import React, { useState } from 'react';

function InputInfor() {
  const [recipeName, setRecipeName] = useState('');
  const [foodName, setFoodName] = useState('');
  const [recipeIntro, setRecipeIntro] = useState('');

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
    console.log('레시피 소개:', recipeIntro);
  };

  return (
    <>
    <div style={{  padding: '1%'}}>
      <h4 style={{textAlign:'left', marginLeft:'2%', marginRight : '15%'}}>2. 기본 정보 입력</h4>
    {/* </div> */}
    <form onSubmit={handleSubmit} style={{marginLeft:'2%', marginRight : '15%', padding:'1%'}}>
        <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom:'1%'}}>
          
          <label htmlFor="recipeName" style={{width:'15%'}} >레시피 제목:</label>
          <input
            type="textarea"
            id="recipeName"
            name="recipeName"
            value={recipeName}
            onChange={handleRecipeNameChange}
            placeholder="레시피 제목을 입력하세요."
          />
        </div>
        <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom:'1%' }}>
          <label htmlFor="foodName" style={{width:'15%'}}>음식 이름:</label>
          <input
            type="text"
            id="foodName"
            name="foodName"
            value={foodName}
            onChange={handleFoodNameChange}
            placeholder="음식 이름을 입력하세요."
          />
        </div>
        <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom:'1%' }}>
          <label htmlFor="recipeIntro" style={{width:'15%'}}>레시피 소개:</label>
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
    </div>
    </>
  );
}

export default InputInfor;
