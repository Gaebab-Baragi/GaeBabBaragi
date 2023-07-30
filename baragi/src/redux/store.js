/* eslint-disable */
import { configureStore, createSlice } from "@reduxjs/toolkit";
import user from "./userSlice";
import recipeSearch from "./searchRecipeSlice";


export default configureStore({
  reducer:{
    user: user.reducer,
    recipeSearch:recipeSearch.reducer,
  }
})