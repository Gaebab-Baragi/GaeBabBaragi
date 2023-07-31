// actions.js
import { SET_RECIPE_NAME, SET_FOOD_NAME, SET_RECIPE_INTRO } from './actionTypes';

export const setRecipeName = (name) => ({
  type: SET_RECIPE_NAME,
  payload: name,
});

export const setFoodName = (name) => ({
  type: SET_FOOD_NAME,
  payload: name,
});

export const setRecipeIntro = (intro) => ({
  type: SET_RECIPE_INTRO,
  payload: intro,
});
