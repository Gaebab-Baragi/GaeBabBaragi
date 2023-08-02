import { createSlice } from "@reduxjs/toolkit";

// 레시피 등록
let recipeRegister= createSlice({
  name:'recipeRegister',
  initialState:{
    image : '',
    recipe_name : '',
    recipe_infor : '',
    material_list : [],
    step_list : [],
  },
  reducers:{
    setImage: (state, action) =>{
      state.image = action.payload
    },
    setRecipeName2: (state, action) => {
      state.recipe_name = action.payload
      console.log('recipe_name : ' + state.recipe_name)
    },
    setRecipeInfor: (state, action)=>{
      state.recipe_infor = action.payload
      console.log('recipe_infor :' + state.recipe_infor)
    },
    setMaterialList: (state, action) =>{
      state.material_list = action.payload;
      console.log('material_list : ' + state.material_list)
    },
    
    setStepList :(state, action) =>{
      state.step_list = action.payload
      console.log(state.step_list)
    },
    }
})

export const {setImage, setRecipeName2, setRecipeInfor, setMaterialList, setStepList} = recipeRegister.actions;

export default recipeRegister;