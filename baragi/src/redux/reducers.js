// reducers.js
import { combineReducers } from 'redux';
import user from './userSlice';
import recipeSearch from './recipeSearchSlice';
import streamingRegister from './streamingRegisterSlice';
import recipeRegister from './recipeRegisterSlice';
import streamingInfo from './streamingInfoSlice';

const rootReducer = combineReducers({
  user : user.reducer,
  recipeSearch: recipeSearch.reducer,
  streamingRegister:streamingRegister.reducer,
  recipeRegister: recipeRegister.reducer,
  streamingInfo: streamingInfo.reducer,
});

export default rootReducer;