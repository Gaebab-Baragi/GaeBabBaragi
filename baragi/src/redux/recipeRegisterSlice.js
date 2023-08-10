import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

// 레시피 등록
let recipeRegister = createSlice({
  
  name: "recipeRegister",
  initialState: {
    title: "과일 타르트 만들기",
    description: "상큼한 디저트 만들어봐요",
    steps: [
      {
        orderingNumber: 1,
        description: "모든 재료를 잘라주세요.",
      },
      {
        orderingNumber: 2,
        description: "재료를 끓여주세요.",
      },
      { orderingNumber: 3, description: "구워주세요" },
      { orderingNumber: 4, description: "플레이팅"},
    ],
    recipeIngredients: [{ ingredientName: "고구마", amount: "1 개" }],
    recipeImage: '',
    recipeVideo: './기본이미지.png',
    // videoUrl : './기본이미지.png'
    stepImages : ['0'],

  },
  reducers: {
    requestFilteredRecipeList: (state) => {
      // console.log(state.recipeIngredients);
      // console.log(state.member.id);
      // axios 요청 보내서 레시피 저장하기
      console.log('1:',state.title, state.description, state.steps, state.recipeIngredients)
      console.log('2:', state.recipeImage)

      const formData = new FormData();
      
      const data = {
        title: state.title,
        description: state.description,
        steps: state.steps,
        recipeIngredients: state.recipeIngredients,
      };
      formData.append(
        "recipeUploadRequestDto",
        new Blob([JSON.stringify(data)], { type: "application/json" })
      );
      formData.append(
        "stepImages",
        new Blob([state.stepImages], { type: "multipart/form-data" })
      );


      console.log('file',state.recipeImage)

      formData.append("recipeImage", state.recipeImage)
      formData.append('recipeVideo',state.recipeVideo) // 이거 이름수정
      console.log('stepImages redux : ', state.stepImages)
      // state.stepImages.map((step)=>{
      //   formData.append('stepImages',step)
      // })
      // formData.append('stepImages',state.stepImages)
      for (let key of formData.keys()) {
        console.log(key);
      }
      for (let value of formData.values()) {
        console.log(value);
      }
      

      axios
        .post("http://localhost:8083/api/recipes/new", formData
        ,  {headers: { "Content-Type": "multipart/form-data" },
      })
      
        .then((res) => {
          console.log("Request successful : ", res);
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
    updateImage : (state,action) =>{
      state.recipeImage = action.payload
      console.log('레시피이미지',state.recipeImage)
    },

    updateVideo : (state,action) =>{
      state.recipeVideo = action.payload
      console.log('비디오', state.recipeVideo) 
    },
    addStepImage : (state,action) =>{
      state.stepImages.push('0')
      console.log('+버튼으로 ',state.stepImages)

    },
    updateStepImage : (state, action) =>{
      console.log(action.payload)
      const { step, selectedImage } = action.payload;
      console.log('스탭플러스버튼',state.stepImages)
      //
      state.stepImages = state.stepImages.map((image, index) => {
        if (index === step - 1) {
          return selectedImage;
        }
        return image;
      });
      //

   
       // 다시 수정하고 +버튼에서 리스트 추가로 아무거나 하나 넣어주는거?
      // state.stepImages = action.payload // 원래는 여기다가 slice 이용해서 추가하는기능
      console.log('스탭 이미지 리스트', state.stepImages)
      state.stepImages.forEach((image, index) => {
        console.log(`인덱스 ${index}: ${image}`);
      });
      state.stepImages = [...state.stepImages];
    },
    deletedStepImage : (state,action) =>{  
      const deletestep = action.payload
      state.stepImages.splice(deletestep-1,1) // 마찬가지로 삭제 요망
      console.log('-버튼',state.stepImages)
    },

    
  },
});

export const { requestFilteredRecipeList , updateRecipeInfor, updateRecipeMaterial, updateStep, updateImage, updateStepImage, deletedStepImage, updateVideo, addStepImage} = recipeRegister.actions;

export default recipeRegister;