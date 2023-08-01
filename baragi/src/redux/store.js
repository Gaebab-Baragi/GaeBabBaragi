/* eslint-disable */
import { configureStore } from "@reduxjs/toolkit";
import user from "./userSlice";
import recipeSearch from "./recipeSearchSlice";
import streamingRegister from "./streamingRegisterSlice";


export default configureStore({     // state 등록 한거임
  reducer:{
    user: user.reducer,
    recipeSearch: recipeSearch.reducer,
    streamingRegister: streamingRegister.reducer
  }
})



