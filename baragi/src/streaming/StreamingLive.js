import Streaming from "./Streaming";
import { useSelector } from "react-redux";

function StreamingLivePage() {
  const streamingInfo = useSelector(state=>state.streamingInfo)
  console.log(streamingInfo)
  return <Streaming sessionId={streamingInfo.meeting_id} nickname={streamingInfo.nickname} />;
}

export default StreamingLivePage;