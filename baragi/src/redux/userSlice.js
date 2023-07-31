import { createSlice } from "@reduxjs/toolkit";

// USER 관련 
let user= createSlice({
  name:'user',
  initialState:{
    id: '', // long
    username: '', // string
    nickname :' ',
    profileUrl: '',
    hostedMeeting: '',
    isLogin : false
  },
  reducers:{
    loginUser: (state, action)=>{
      state.id= action.payload.id;
      state.username = action.payload.username;
      state.nickname = action.payload.nickname;
      state.profileUrl= action.payload.profileUrl;
      state.hostedMeeting = action.payload.hostedMeeting;
      state.isLogin = true;
    },
    clearUser: (state) =>{
      state.id = '';
      state.username = '';
      state.nickname = '';
      state.profileUrl = '';
      state.hostedMeeting = '';
      state.isLogin = false;
    }
  }
})

export const {loginUser, clearUser} = user.actions;

export default user;