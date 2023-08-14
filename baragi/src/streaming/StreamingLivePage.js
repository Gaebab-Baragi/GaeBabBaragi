import { useEffect, useState } from "react";
import Streaming from "./Streaming";
import { useSelector } from "react-redux";
import axios from "axios";

function StreamingLivePage() {
  const streamingInfo = useSelector(state=>state.streamingInfo)
  const user = useSelector(state=>state.user)
  const [recipeData, setRecipeData] = useState([])
  // recipe 정보 가져오기?

  useEffect(()=>{
    axios.get(process.env.REACT_APP_BASE_URL +`/api/recipes/${streamingInfo.recipe_id}`)
    .then((res)=>{
      console.log(res.data)
      setRecipeData(res.data)
    })
    .catch((err)=>{
      console.log(err)
    })
  },[])

  return(
    // console.log('아ㅏㅏㅏㅏㅏㅏ!!!!!!!!!!!!!!!')
    <div>
      <Streaming 
              streamingInfo={streamingInfo} 
              sessionId={streamingInfo.meeting_id} 
              nickname={user.nickname} 
              host_nickname = {streamingInfo.host_nickname}
              recipeData = {recipeData}
              userProfileUrl={user.profileUrl}
              />
      
    </div>

  ) 
}

export default StreamingLivePage;