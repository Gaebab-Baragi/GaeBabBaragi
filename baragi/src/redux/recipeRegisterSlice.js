import { createSlice } from "@reduxjs/toolkit";

// 레시피 검색  
let recipeRegister= createSlice({
  name:'recipeRegister',
  initialState:{
    step:[],
    
  },
  reducers:{
    request1: (state, action)=>{
      // axios 요청 보내서 레시피 저장하기

      
    },

    
  }
})

export const {request1} = recipeRegister.actions;

export default recipeRegister;