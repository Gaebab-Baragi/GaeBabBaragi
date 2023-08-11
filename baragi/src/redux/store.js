/* eslint-disable */
import {configureStore, getDefaultMiddleware } from "@reduxjs/toolkit";
import { persistReducer } from "redux-persist";
import sessionStorage from "redux-persist/es/storage/session";
import rootReducer from "./reducers";
import thunk from "redux-thunk";
import { getDefaultNormalizer } from "@testing-library/react";

const persistConfg ={
  key : 'root',
  storage : sessionStorage,
  whitelist: ['user', 'streamingInfo','recipeSearch'],
}

const persistedReducer = persistReducer(persistConfg, rootReducer);

const store = configureStore({   
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(thunk),
})

export default store;