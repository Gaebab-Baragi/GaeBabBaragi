import axios from "axios";
import { useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from 'react-router-dom';
import { useDispatch } from "react-redux";
import { setStreamingInfo } from "../redux/streamingInfoSlice";

function StreamingListPage() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [streamingList, setStreamingList] = useState([]);
  const user = useSelector((state) => (state.user));
  const nickname = user.nickname

  function getList() {
    axios.get( '/api/meetings')
      .then((res) => {
        console.log('get list is successful : ', res.data);
        setStreamingList(res.data);
      })
      .catch((err) => {
        console.log('error : ', err);
      });
  }

  function checkMeeting(streamingItem) {
    console.log(streamingItem.id)
    axios.get(`/api/meetings/join-request/${streamingItem.id}`)
    .then((res)=>{
      console.log('request success : ', res.data);

      if (res.data.status != 'fail') {
        alert(res.data.message);
      } else {
        const data = {
          meeting_id: streamingItem.id,
          title : streamingItem.title,
          recipe_id: streamingItem.recipe_id,
          host_nickname: streamingItem.host_nickname,
          max_participant: streamingItem.max_participant,
          start_time:streamingItem.start_time
        }
        dispatch(setStreamingInfo(data))
        navigate('/streaming-live')
      }

    })
    .catch((err)=>{
      console.log('error occured' + err)
    })
  }
  return (
    <div>
      <button onClick={getList}>목록</button>
      <div>
        {streamingList.map((streamingItem) => (
          <div key={streamingItem.id}>
            <p>ID: {streamingItem.id}</p>
            <p>Title: {streamingItem.title}</p>
            <p>Description: {streamingItem.description}</p>
            <button onClick={()=>checkMeeting(streamingItem)}>CHECK MEETING AVAILABILITY</button>
            <hr/>
          </div>
        ))}
      </div>
    </div>
  );
}

export default StreamingListPage;
