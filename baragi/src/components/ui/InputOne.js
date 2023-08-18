import React, { useState ,useEffect} from 'react';
import { useDispatch } from 'react-redux';
import {requestRecipeTitle, requestFoodName, requestRecipeInformation} from '../../redux/inforSlice'

function InputOne() {
  const [InputBox, setInputBoxName] = useState('');

  const handleRecipeNameChange = (e) => {
    setInputBoxName(e.target.value);
    // dispatch(requestRecipeTitle(e.target.value));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // 레시피 이름과 음식 이름을 서버로 전송하거나 다른 작업을 수행하는 로직을 추가할 수 있습니다.
    // console.log(' 이름:', InputBox);
  };

  return (
    <>
    {/* <div style={{  padding: '1%'}}> */}

    {/* </div> */}
    {/* <form onSubmit={handleSubmit} style={{marginLeft:'2%', marginRight : '15%', padding:'1%'}}> */}
        <div style={{ display: 'flex', justifyContent: 'space-between', marginBottom:'1%'}}>
          
          <label htmlFor="recipeName" style={{width:'15%'}} >레시피 제목:</label>
          <input
            type="text"
            id="recipeName"
            name="recipeName"
            value={recipeName}
            onChange={handleRecipeNameChange}
            placeholder="레시피 제목을 입력하세요."
          />
        </div>
        {/* <button type="submit">제출</button> */}
    {/* </form> */}
    {/* </div> */}
    </>
  );
}

export default InputOne;
