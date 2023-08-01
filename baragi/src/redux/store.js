/* eslint-disable */
import { combineReducers, configureStore } from "@reduxjs/toolkit";
import { persistReducer } from "redux-persist";
import sessionStorage from "redux-persist/es/storage/session";
import user from "./userSlice";
import recipeSearch from "./recipeSearchSlice";
import streamingRegister from "./streamingRegisterSlice";
import rootReducer from "./reducers";
  // state 등록 한거임

const persistConfg ={
  key : 'root',
  storage : sessionStorage,
}

const persistedReducer = persistReducer(persistConfg, rootReducer);

const store = configureStore({     // state 등록 한거임
  reducer: persistedReducer

})


export default store;

