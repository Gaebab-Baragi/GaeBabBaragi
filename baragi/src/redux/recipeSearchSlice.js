import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

export const updateFilteredList = () =>({
  type:"recipeSearch/updateFilteredList"
});

// 레시피 검색  
let recipeSearch= createSlice({
  name:'recipeSearch',
  initialState:{
    keyword:'',
    ingredients:[],
    dogs:[],
    filteredList:[],
    requestHappen: false,
  },
  reducers:{
    requestFilteredRecipeList: (state)=>{
      let tempIngredient = state.ingredients
      if (state.ingredients.length === 0) {
        tempIngredient = null;
      } 
      console.log(JSON.parse(JSON.stringify(state.dogs)))
      const data = {
        title : state.keyword,
        ingredients: tempIngredient,
        pets: state.dogs
      }
      console.log('보내는 데이터임',data)
      // axios 요청 보내서 레시피 저장하기
      axios.post(process.env.REACT_APP_BASE_URL +'/api/recipes/searchlike', data)
        .then((res)=>{
          console.log('필터링 된 목록 가져오기 성공 : ' , res.data)
          state.filteredList = res.data
        })
        .catch((err)=>{
          console.log('필터링 데이터 가져오지 못함 :' + err )
        })    
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
      console.log('redux 재료 업데이트 됨', state.ingredients)
      // console.log(state.ingredients)
    },
    updateDogs: (state, action) =>{
      const tmp = []
      action.payload.forEach((dog) => {
        tmp.push({'id':dog})
      });
      state.dogs = tmp
      console.log('redux 강아지 업데이트 됨', state.dogs)
    },
  }
})

export const {updateKeyword, updateIngredients, updateDogs, requestFilteredRecipeList,updatePetIds} = recipeSearch.actions;

export default recipeSearch;
