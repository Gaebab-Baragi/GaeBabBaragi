/* eslint-disable */
import { configureStore } from "@reduxjs/toolkit";
import user from "./userSlice";
import recipeSearch from "./searchRecipeSlice";
import recipeInfor from "./inforSlice";





export default configureStore({     // state 등록 한거임
  reducer:{
    user: user.reducer,
    recipeSearch: recipeSearch.reducer,
    recipeInfor: recipeInfor.reducer,
  }
})



