import Streaming from "./Streaming";
import { useSelector } from "react-redux";

function StreamingLivePage() {
  const streamingInfo = useSelector(state=>state.streamingInfo)
  const user = useSelector(state=>state.user)
  console.log(streamingInfo)
  return(
    // console.log('아ㅏㅏㅏㅏㅏㅏ!!!!!!!!!!!!!!!')
    <div>
      <Streaming 
              streamingInfo={streamingInfo} 
              sessionId={streamingInfo.meeting_id} 
              nickname={user.nickname} 
              host_nickname = {streamingInfo.host_nickname}
              />
      
    </div>

  ) 
}

export default StreamingLivePage;