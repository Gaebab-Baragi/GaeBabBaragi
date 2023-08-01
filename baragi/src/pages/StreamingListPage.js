import axios from "axios";
import { useState } from "react";

const BASE_URL = 'http://localhost:9999';

function StreamingListPage() {
  const [streamingList, setStreamingList] = useState([]);

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

  function startMeeting(streamingItem) {
    console.log(streamingItem.id)
    axios.post(BASE_URL + `/meetings/start/${streamingItem.id}`)
    .then((res)=>{
      console.log('request success : ', res)
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
            <button onClick={()=>startMeeting(streamingItem)}>START STREAMING</button>
            <hr/>
          </div>
        ))}
      </div>
    </div>
  );
}

export default StreamingListPage;
