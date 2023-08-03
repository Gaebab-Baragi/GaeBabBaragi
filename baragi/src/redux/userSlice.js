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
      console.log(action.payload);
      state.id= action.payload.id;
      state.username = action.payload.username;
      state.nickname = action.payload.nickname;
      state.profileUrl= action.payload.profile_url;
      state.hostedMeeting = action.payload.hosted_meeting;
      state.isLogin = true;
    },
    clearUser: (state) =>{
      state.isLogin = false;
    }
  }
})

export const {loginUser, clearUser} = user.actions;

export default user;