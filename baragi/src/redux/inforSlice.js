import { createSlice } from "@reduxjs/toolkit";

// 레시피 검색  
let recipeInfor= createSlice({
  name:'recipeInfor',
  initialState:{
    
    recipetitle:'',
    foodname:'',
    recipeinformation:'',
  },
  reducers:{
    requestRecipeTitle: (state, action)=>{
      //
    },
    requestFoodName: (state, action) =>{
      //
    },
    requestRecipeInformation: (state, action) =>{
        //
    },

  }
})

export const {requestRecipeTitle, requestFoodName,requestRecipeInformation} = recipeInfor.actions;

export default recipeInfor;