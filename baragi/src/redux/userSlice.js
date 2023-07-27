import { createSlice } from "@reduxjs/toolkit";

// USER 관련 
let user= createSlice({
  name:'user',
  initialState:{
    userId:'', // long
    loginId:'', // string
    isLogin: null,
  },
  reducers:{
    loginUser: (state, action)=>{
      // state update needed
      
    },
    clearUser: (state) =>{
      // state update
    }
  }
})

export const {loginUser} = user.actions;

export default user;