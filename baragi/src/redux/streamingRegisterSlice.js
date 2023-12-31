import { createSlice } from "@reduxjs/toolkit";
import axios from "axios";

let streamingRegister= createSlice({
  name:'streamingRegister',
  initialState:{
    password:null,
    title:'',
    max_participant:1,
    description:'',
    start_time:'',
    // 수정해야됨
    recipe_id:1,
  },
  reducers:{
    setPassword: (state, action) =>{
      state.password = action.payload
    },
    setTitle: (state, action) => {
      state.title = action.payload
      // console.log('streaming title : ' + state.title)
    },
    setDescription: (state, action) =>{
      state.description = action.payload;
      // console.log('streaming description : ' + state.description)
    },
    setMaxParticipant: (state, action)=>{
      state.max_participant = action.payload
      // console.log('max participant :' + state.max_participant)
    },
    setStartTime: (state, action) =>{
      state.start_time = action.payload;
      // console.log('start time : ' + state.start_time)
      // console.log('start time : ' + typeof(state.start_time))
    },
    setRecipeId : (state, action) => {
      // 추후 수정
      state.recipe_id = action.payload;
    },
    requestStreamingReservation: (state) =>{
      const data = {
        password: state.password,
        title: state.title, 
        max_participant: state.max_participant,
        description : state.description,
        // member_id: state.member_id,
        start_time: state.start_time,
        recipe_id: state.recipe_id,
      };
      // console.log(data)
      // console.log(typeof(state.start_time), state.start_time)

      axios.post(process.env.REACT_APP_BASE_URL +"/api/meetings", data)
        .then((response) => {
          // Handle the response if needed
          // console.log("Request successful:", response.data);
        })
        .catch((error) => {
          // Handle errors if necessary
          // console.error("Error sending request:", error);
        });
    },
  }
})

export const {setTitle, setDescription, setMaxParticipant, setMemberId, setStartTime, setRecipeId, requestStreamingReservation} = streamingRegister.actions;

export default streamingRegister; 