import { createSlice } from "@reduxjs/toolkit";

// USER 관련 
let user= createSlice({
  name:'user',
  initialState:{
    id: '', // long
    username: '', // string
    nickname :' ',
    profileUrl: '',
    isLogin : false
  },
  reducers:{
    loginUser: (state, action)=>{
      console.log(action.payload);
      state.id= action.payload.id;
      state.username = action.payload.username;
      state.nickname = action.payload.nickname;
      state.profileUrl= action.payload.profile_url;
      state.isLogin = true;
    },
    clearUser: (state) =>{
      state.isLogin = false;
      state.id="";
      state.username='';
      state.nickname='';
      state.profileUrl='';
    }
  }
})

export const {loginUser, clearUser} = user.actions;

export default user;