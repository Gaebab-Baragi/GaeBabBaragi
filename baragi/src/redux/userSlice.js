import { createSlice } from "@reduxjs/toolkit";

// USER 관련 
let user= createSlice({
  name:'user',
  initialState:{
    id: '', // long
    username: '', // string
    nickname :' ',
    profileUrl: '',
    hostedMeeting: ''
  },
  reducers:{
    loginUser: (state, action)=>{
      state.id= action.payload.id;
      state.username = action.payload.username;
      state.nickname = action.payload.nickname;
      state.profileUrl= action.payload.profileUrl;
      state.hostedMeeting = action.payload.hostedMeeting;
      console.log(action.payload);
    },
    clearUser: (state) =>{
      // state update
    }
  }
})

export const {loginUser, clearUser} = user.actions;

export default user;