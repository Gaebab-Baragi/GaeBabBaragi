/* eslint-disable */
import {configureStore } from "@reduxjs/toolkit";
import { persistReducer } from "redux-persist";
import sessionStorage from "redux-persist/es/storage/session";
import rootReducer from "./reducers";

const persistConfg ={
  key : 'root',
  storage : sessionStorage,
  whitelist: ['user', 'streamingInfo'],
}

const persistedReducer = persistReducer(persistConfg, rootReducer);

const store = configureStore({   
  reducer: persistedReducer
})


export default store;
