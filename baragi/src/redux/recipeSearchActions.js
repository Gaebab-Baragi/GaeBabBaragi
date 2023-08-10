import { recipeSearchActions } from "./recipeSearchSlice";

export const updateDogs = (dogsSelected) => {
  return async(dispatch)=>{
    console.log('첫번째 async 함수')
    dispatch(recipeSearchActions.updateDogs(dogsSelected));

      async() => {
        console.log('두번째 async 함수');
        dispatch(recipeSearchActions.requestFilteredRecipeList())
      }
  }
}