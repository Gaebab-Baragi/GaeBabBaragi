import { createSlice } from "@reduxjs/toolkit";
// import axios from "axios";

// pet 등록
let objectDetect= createSlice({
  name:'objectDetect',
  initialState:{
    Ingredients :[],

  },
  reducers:{
    setIngredients: (state, action)=>{
      state.Ingredients = action.payload
      state.Ingredients = Object.values(state.Ingredients)
      console.log('redux Ingredients name : ',state.Ingredients)
    },
    AddIngredients: (state, action)=>{
        state.Ingredients.push(action.payload)
    }
  
  }
})

export const {setIngredients, AddIngredients} = objectDetect.actions;

export default objectDetect;