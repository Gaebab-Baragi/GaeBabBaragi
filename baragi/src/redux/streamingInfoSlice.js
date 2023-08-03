import { createSlice } from "@reduxjs/toolkit";

let streamingInfo= createSlice({
  name:'streamingInfo',
  initialState:{
    meeting_id:null,
    title:'',
    recipe_id:'',
    max_participant:null,
    host_nickname:null,
    start_time:null,
  },
  reducers:{
    setStreamingInfo: (state, action) => {
      state.title = action.payload.title
      state.recipe_id=action.payload.recipe_id
      state.max_participant = action.payload.max_participant
      state.host_nickname = action.payload.host_nickname
      state.start_time = action.payload.start_time
      state.meeting_id = action.payload.meeting_id
    }
  }
})

export const {setStreamingInfo} = streamingInfo.actions;

export default streamingInfo;