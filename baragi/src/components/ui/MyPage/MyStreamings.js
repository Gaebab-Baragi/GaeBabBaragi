import axios from "axios";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from 'react-router-dom';
import { useDispatch } from "react-redux";
import { setStreamingInfo } from "../../../redux/streamingInfoSlice";
import StreamingCardComponent from "../StreamingCard/StreamingCard";
import styled from 'styled-components';

const MyStreamings= () => {
  const [streamingList, setStreamingList] = useState([]);
  const user = useSelector((state) => (state.user));
  const nickname = <user className="nickname"></user>;
  // streaming 전체 list 가져오기
  useEffect(() => {
    axios.get(process.env.REACT_APP_BASE_URL +'/api/meetings/my-meetings')
      .then((res) => {
        // console.log('get list is successful : ', res.data);
        setStreamingList(res.data);
      })
      .catch((err) => {
        // console.log('error : ', err);
      });
  }, []);

  return (
      <div className="streaming-list container position-relative">
        <div className="streamingList row gy-4 mt-2">
          {streamingList.map((streamingItem) => (
            <div className="streaming-card-component col-xl-4 col-md-6">
              <StreamingCardComponent
                key={streamingItem.id}
                title={streamingItem.title}
                description={streamingItem.description}
                host_nickname={streamingItem.host_nickname}
                host_profile_url={streamingItem.host_profile_url}
                max_participant={streamingItem.max_participant}
                start_time={streamingItem.start_time}
                status={streamingItem.status}
                current_participants={streamingItem.current_participants}
                meeting_id={streamingItem.id}
                recipe_id={streamingItem.recipe_id}
                recipe_image_url={streamingItem.recipe_image_url}
                is_private_room={streamingItem.is_private_room}
              />
            </div>
          ))}
        </div>
      </div>
  );
}

export default MyStreamings;