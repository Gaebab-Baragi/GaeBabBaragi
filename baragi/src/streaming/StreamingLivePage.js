import Streaming from "./Streaming";
import { useSelector } from "react-redux";

function StreamingLivePage() {
  const streamingInfo = useSelector(state=>state.streamingInfo)
  const user = useSelector(state=>state.user)
  console.log(streamingInfo)
  return <Streaming 
          streamingInfo={streamingInfo} 
          sessionId={streamingInfo.meeting_id} 
          nickname={user.nickname} 
          host_nickname = {streamingInfo.host_nickname}
          />;
}

export default StreamingLivePage;