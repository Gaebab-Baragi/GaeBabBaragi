import { createSlice } from "@reduxjs/toolkit";

// 레시피 검색  
let recipeRegister= createSlice({
  name:'recipeRegister',
  initialState:{
    
  },
  reducers:{
    requestFilteredRecipeList: (state, action)=>{
      // axios 요청 보내서 레시피 저장하기
      console.log(action)
      state.requestHappen=true;
      
    },
    updateKeyword: (state, action) =>{
      // 레시피 제목 검색 키워드 저장
      state.keyword = action.payload;
      // console.log(state.keyword)
    },
    updateIngredients: (state, action) =>{
      state.ingredients = action.payload.map((ingredient) => ingredient.label);
      // console.log(state.ingredients)
    },
    updateDogs: (state, action) =>{
      state.dogs = action.payload
      // console.log(state.dogs)
    }
  }
})

export const {updateKeyword, updateIngredients, updateDogs, requestFilteredRecipeList} = recipeRegister.actions;

export default recipeRegister;