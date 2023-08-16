import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

export const updateFilteredList = () =>({
  type:"recipeSearch/updateFilteredList"
});

// 레시피 검색  
let recipeSearch= createSlice({
  name:'recipeSearch',
  initialState:{
    title:'',
    ingredients:[],
    dogs:[],
    filteredList:[],
    requestHappen: false,
  },
  reducers:{
    updateKeyword: (state, action) =>{
      // 레시피 제목 검색 키워드 저장
      state.title = action.payload;
      console.log('keyword updated: ' + state.keyword)
    },
    updateIngredients: (state, action) =>{
      const temp = []
      const ingredientArray = action.payload
      ingredientArray.forEach(ingredient => {
        temp.push({'name' : ingredient.label})
      });
      console.log(temp)
      state.ingredients = temp
      console.log('redux 재료 업데이트 됨', state.ingredients)
      // console.log(state.ingredients)
    },
    updateIngredients2: (state,action) =>{
      const ingredientArray = action.payload
      console.log('1:' , ingredientArray)
    },

    updateDogs: (state, action) =>{
      const tmp = []
      action.payload.forEach((dog) => {
        tmp.push({'id':dog})
      });
      state.dogs = tmp
      console.log('redux 강아지 업데이트 됨', state.dogs)
    },
  }
})

export const {updateKeyword, updateIngredients,updateIngredients2, updateDogs} = recipeSearch.actions;

export default recipeSearch;
