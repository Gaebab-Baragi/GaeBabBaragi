import axios from "axios";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from 'react-router-dom';
import { useDispatch } from "react-redux";
import { setStreamingInfo } from "../redux/streamingInfoSlice";
import StreamingCardComponent from "../components/ui/StreamingCard";
import styled from 'styled-components';

const CenteredContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 10px;
  // background-color:red;
`;

const ItemsContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  gap: 20px;
  max-width: 1200px;
  margin-left: 10%;
  margin-right: 10%;
  // background-color:aqua;
`;

const StreamingCardWrapper = styled.div`
  flex: 0 0 calc(25% - 20px); /* Equivalent to col-md-3 */
`;

function StreamingListPage() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [streamingList, setStreamingList] = useState([]);
  const user = useSelector((state) => (state.user));
  const nickname = <user className="nickname"></user>;
  
  useEffect(() => {
    axios.get(process.env.REACT_APP_BASE_URL +'/api/meetings')
      .then((res) => {
        console.log('get list is successful : ', res.data);
        setStreamingList(res.data);
      })
      .catch((err) => {
        console.log('error : ', err);
      });
  }, []);

  return (
    <CenteredContainer>
      <h1>방송 목록</h1>
      <ItemsContainer>
        {streamingList.map((streamingItem) => (
          <StreamingCardWrapper key={streamingItem.id}>
            <StreamingCardComponent
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
            />
          </StreamingCardWrapper>
        ))}
      </ItemsContainer>
    </CenteredContainer>
  );
}

export default StreamingListPage;
