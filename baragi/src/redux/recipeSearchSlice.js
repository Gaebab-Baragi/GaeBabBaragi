import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import { act } from "react-dom/test-utils";

const BASE_URL = 'http://localhost:9999'

// 레시피 검색  
let recipeSearch= createSlice({
  name:'recipeSearch',
  initialState:{
    keyword:'',
    ingredients:[],
    dogs:[],
    filteredList:[],
    requestHappen: false
  },
  reducers:{
    requestFilteredRecipeList: (state, action)=>{
      let tempIngredient = state.ingredients
      if (state.ingredients.length == 0) {
        tempIngredient = null;
      } 
      const data = {
        title : state.keyword,
        ingredients: tempIngredient
      }

      // axios 요청 보내서 레시피 저장하기
      axios.post(BASE_URL + '/recipes/searchlike', data)
        .then((res)=>{
          console.log('request success : ' , res.data)
        })
        .catch((err)=>{
          console.log('error :' + err )
        })
      state.requestHappen=true;
      
    },
    updateKeyword: (state, action) =>{
      // 레시피 제목 검색 키워드 저장
      state.keyword = action.payload;
      console.log('keyword updated: ' + state.keyword)
    },
    updateIngredients: (state, action) =>{
      const temp = []
      const ingredientArray = action.payload
      ingredientArray.forEach(ingredient => {
        temp.push({'name' : ingredient.label})
      });
      console.log(temp)
      state.ingredients = temp
      // console.log(state.ingredients)
    },
    updateDogs: (state, action) =>{
      state.dogs = action.payload
      // console.log(state.dogs)
    }
  }
})

export const {updateKeyword, updateIngredients, updateDogs, requestFilteredRecipeList} = recipeSearch.actions;

export default recipeSearch;