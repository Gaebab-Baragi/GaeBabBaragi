import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

// pet 등록
let petRegister= createSlice({
  name:'recipeSearch',
  initialState:{
    petImage:'',
    name:'',
    forbiddenIngredients:[]
  },
  reducers:{
    setPetName: (state, action)=>{
      state.name = action.payload
      console.log('redux pet name : ', state.name)
    },
    setPetImage: (state, action)=>{
      state.petImage = action.payload
      console.log('redux image',state.petImage)
    },
    setForbiddenIngredients: (state, action) =>{
      state.forbiddenIngredients = action.payload
    }
  }
})

export const {setPetName,setPetImage,setForbiddenIngredients} = petRegister.actions;

export default petRegister;