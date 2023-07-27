import { createSlice } from "@reduxjs/toolkit";

// 레시피 검색  
let recipeSearch= createSlice({
  name:'recipeSearch',
  initialState:{
    keyword:'',
    ingredients:[],
    dog:''
  },
  reducers:{
    requestFilteredRecipeList: (state, action)=>{
      // state update 
      
    },
    updateKeyword: (state, action) =>{
      // 레시피 제목 검색 키워드 저장
      state.keyword = action.payload;
      // console.log(state.keyword)
    },
    updateIngredients: (state, action) =>{
      state.ingredients = action.payload.map((ingredient) => ingredient.label);
      // console.log(state.ingredients)
    }
  }
})

export const {updateKeyword, updateIngredients} = recipeSearch.actions;

export default recipeSearch;