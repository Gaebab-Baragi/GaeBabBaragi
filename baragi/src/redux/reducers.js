// reducers.js
import { combineReducers } from 'redux';
import { SET_RECIPE_NAME, SET_FOOD_NAME, SET_RECIPE_INTRO } from './actionTypes';
import user from './userSlice';

const recipeNameReducer = (state = '', action) => {
  switch (action.type) {
    case SET_RECIPE_NAME:
      return action.payload;
    default:
      return state;
  }
};

const foodNameReducer = (state = '', action) => {
  switch (action.type) {
    case SET_FOOD_NAME:
      return action.payload;
    default:
      return state;
  }
};

const recipeIntroReducer = (state = '', action) => {
  switch (action.type) {
    case SET_RECIPE_INTRO:
      return action.payload;
    default:
      return state;
  }
};

const rootReducer = combineReducers({
  recipeName: recipeNameReducer,
  foodName: foodNameReducer,
  recipeIntro: recipeIntroReducer,
  user : user.reducer
});

export default rootReducer;
