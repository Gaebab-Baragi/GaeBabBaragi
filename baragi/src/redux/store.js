/* eslint-disable */
import { configureStore, createSlice } from "@reduxjs/toolkit";
import user from "./userSlice";


export default configureStore({
  reducer:{
    user: user.reducer
  }
})