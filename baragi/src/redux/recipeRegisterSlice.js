import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

// 레시피 등록
let recipeRegister = createSlice({
  
  name: "recipeRegister",
  initialState: {
    title: "과일 타르트 만들기",
    description: "상큼한 디저트 만들어봐요",
    member: {
      id: 7,
    },
    recipeIngredients: [{ ingredientName: "고구마", amount: "1 개" }],
    steps: [
      {
        orderingNumber: 1,
        description: "모든 재료를 잘라주세요.",
        imgLocalPath: null,
      },
      {
        orderingNumber: 2,
        description: "재료를 끓여주세요.",
        imgLocalPath: null,
      },
      { orderingNumber: 3, description: "구워주세요", imgLocalPath: null },
      { orderingNumber: 4, description: "플레이팅", imgLocalPath: null },
    ],

    videoLocalPath: null,
    temp : []
  },
  reducers: {
    requestFilteredRecipeList: (state) => {
      // console.log(state.recipeIngredients);
      // console.log(state.member.id);
      // axios 요청 보내서 레시피 저장하기
      const data = {
        title: state.title,
        description: state.description,
        member: state.member,
        recipeIngredients: state.recipeIngredients,
        steps: state.steps,
        imgLocalPath: state.imgLocalPath,
        videoLocalPath: state.videoLocalPath,
        
      };
      
      console.log(data)
      axios
        .post("http://localhost:8083/api/recipes/new", data)
        .then((res) => {
          console.log("Request successful : ", res.data);
        })
        .catch((err) => {
          console.log("Error sending request : ", err);
        });
    },

    updateRecipeInfor : (state, action) => {
      state.title = action.payload[0];
      state.description = action.payload[1];
      console.log('레시피 이름 : ', state.title ,' 레시피 소개 : ', state.description)
    },
    updateRecipeMaterial : (state, action) =>{
      console.log('재료 redux: ',action.payload)
      state.recipeIngredients = action.payload //['ingredientName']
      console.log('inputMats 변경되었습니다:', state.recipeIngredients);
    },
    updateStep : (state,action) =>{
      state.steps = action.payload
      console.log('step 변경됨', state.steps)
    },
    updateStepImage : (state,action) =>{
      state.temp =action.payload
      console.log('스텝이미지 변경', state.temp)
    },
    // updateKeyword: (state, action) =>{
    //   // 레시피 제목 검색 키워드 저장
    //   state.keyword = action.payload;
    //   console.log('keyword updated: ' + state.keyword)
    // },
  
  },
});

export const { requestFilteredRecipeList , updateRecipeInfor, updateRecipeMaterial, updateStep, updateStepImage} = recipeRegister.actions;

export default recipeRegister;