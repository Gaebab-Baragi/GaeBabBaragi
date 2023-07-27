import { createSlice } from "@reduxjs/toolkit";

// USER 관련 
let user= createSlice({
  name:'user',
  initialState:{
    id: '', // long
    username: '', // string
    nickname :' ',
    email : '',
    profileUrl: '',
    hostedMeeting: '',
    googleAccount: '',
    kakaoAccount: '',
    naverAccount: '',
  },
  reducers:{
    loginUser: (state, action)=>{
      state.id= action.payload.id;
      state.username = action.payload.username;
      state.nickname = action.payload.nickname;
      state.email = action.payload.email;
      state.profileUrl= action.payload.profileUrl;
      state.hostedMeeting = action.payload.hostedMeeting;
      state.googleAccount= action.payload.googleAccount;
      state.kakaoAccount = action.payload.kakaoAccount;
      state.naverAccount = action.payload.naverAccount;
    },
    clearUser: (state) =>{
      // state update
    }
  }
})

export const {loginUser} = user.actions;

export default user;