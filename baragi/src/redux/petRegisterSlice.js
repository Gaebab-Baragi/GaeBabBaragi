import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

// pet 등록
let petRegister= createSlice({
  name:'recipeSearch',
  initialState:{
    petImage:'',
    name:'',
    forbiddenIngredients:[]
  },
  reducers:{
    setPetName: (state, action)=>{
      state.name = action.payload
      // console.log('redux pet name : ', state.name)
    },
    setPetImage: (state, action)=>{
      state.petImage = action.payload
      // console.log('redux image',state.petImage)
    },
    setForbiddenIngredients: (state, action) =>{
      let tmp = []
      action.payload.map((i)=>{
        tmp.push(i.value)
      })
      state.forbiddenIngredients = tmp

    },
    sendPetRegisterRequest: (state) =>{
      const formData = new FormData();
      formData.append("petImage", state.petImage);
      const datas = {
        name: state.name,
        forbiddenIngredients: state.forbiddenIngredients
      };
      formData.append(
        "dto",
        new Blob([JSON.stringify(datas)], { type: "application/json" })
      );
      for (let key of formData.keys()) {
        // console.log(key);
      }

      for (let value of formData.values()) {
        // console.log(value);
      }

      axios
        .post(process.env.REACT_APP_BASE_URL +"/api/pet", formData, {
          headers: { "Content-Type": "multipart/form-data" },
        })
        .then((res) => {
          // console.log("axios success :", res.data);
        })
        .catch((err) => {
          // console.log("error : ", err);
        });
    }
  }
})

export const {setPetName,setPetImage,setForbiddenIngredients, sendPetRegisterRequest} = petRegister.actions;

export default petRegister;