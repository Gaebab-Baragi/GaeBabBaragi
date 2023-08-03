import axios from "axios";
import { useState } from "react";
import Streaming from "../streaming/Streaming";
import { useSelector } from "react-redux";


const BASE_URL = 'http://localhost:9999';

function StreamingListPage() {
  const [streamingList, setStreamingList] = useState([]);
  const user = useSelector((state) => (state.user));

  function getList() {
    axios.get(BASE_URL + '/meetings')
      .then((res) => {
        console.log('get list is successful : ', res.data);
        setStreamingList(res.data);
      })
      .catch((err) => {
        console.log('error : ', err);
      });
  }

  function joinMeeting(streamingItem) {
    console.log(streamingItem.id)
    axios.get(BASE_URL + `/meetings/join-request/${streamingItem.id}`)
    .then((res)=>{
      console.log('request success : ', res.data);

      if (res.data.status === 'fail') {
        alert(res.data.message);
      } else {
        // openvidu 세션 & 토근 요청 axios
        console.log('openvidu axios request ')
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
            <button onClick={()=>joinMeeting(streamingItem)}>JOIN</button>
            <Streaming sessionId={streamingItem.id} user={user}/>
            <hr/>
          </div>
        ))}
      </div>
    </div>
  );
}

export default StreamingListPage;
