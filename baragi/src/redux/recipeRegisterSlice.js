import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";
const BASE_URL = 'http://localhost:9999'

// 레시피 검색
let recipeRegister = createSlice({
  name: "recipeRegister",
  initialState: {
    title: "과일 타르트 만들기",
    description: "상큼한 디저트 만들어봐요",
    member: {
      id: 1,
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
    imgLocalPath: "C:\\Users\\SSAFY\\Desktop\\dogExample.jpg",
    videoLocalPath: null,
  },
  reducers: {
    requestFilteredRecipeList: (state) => {
      console.log(state.recipeIngredients);
      console.log(state.member.id);
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

      axios
        .post(BASE_URL + "/recipes/new", data)
        .then((res) => {
          console.log("Request successful : ", res.dat);
        })
        .catch((err) => {
          console.log("Error sending request : ", err);
        });
    },
  },
});

export const { requestFilteredRecipeList } = recipeRegister.actions;

export default recipeRegister;