import React, { useState } from 'react';

function InputInfor() {
  const [recipeName, setRecipeName] = useState('');

  const handleRecipeNameChange = (e) => {
    setRecipeName(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // 레시피 이름을 서버로 전송하거나 다른 작업을 수행하는 로직을 추가할 수 있습니다.
    console.log('레시피 이름:', recipeName);
  };

  return (
    <form onSubmit={handleSubmit}>
      <label htmlFor="recipeName">레시피 이름:</label>
      <input
        type="text"
        id="recipeName"
        name="recipeName"
        value={recipeName}
        onChange={handleRecipeNameChange}
        placeholder="레시피 이름을 입력하세요."
      />
   

      <button type="submit">제출</button>
    </form>
  );
}

export default InputInfor;
