import axios from "axios";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from 'react-router-dom';
import { useDispatch } from "react-redux";
import { setStreamingInfo } from "../../../redux/streamingInfoSlice";
import StreamingCardComponent from "../StreamingCard";
import styled from 'styled-components';

const CenteredContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 10px;
`;

const ItemsContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  max-width: 1200px;
  margin-left: 10%;
  margin-right: 10%;
`;

const MyStreamings= () => {
const navigate = useNavigate();
    const dispatch = useDispatch();
    const [streamingList, setStreamingList] = useState([]);
    const user = useSelector((state) => (state.user));
    const nickname = <user className="nickname"></user>;
    
    useEffect(() => {
        axios.get(process.env.REACT_APP_BASE_URL +"/api/meetings/my-meetings")
        .then((res) => {
            if (res.status === 200){
                setStreamingList(res.data);
            }
        })
        .catch((res) => {
            console.log("미팅 정보를 찾을 수 없습니다.");
        })
    }, [])

    const [itemsPerRow, setItemsPerRow] = useState(getItemsPerRow());

    useEffect(() => {
        const handleResize = () => {
            setItemsPerRow(getItemsPerRow());
        };
        
        window.addEventListener('resize', handleResize);

        return () => {
            window.removeEventListener('resize', handleResize);
        };
    }, []);

    function getItemsPerRow() {
    const screenWidth = window.innerWidth;
    if (screenWidth >= 1200) {
        return 4;
    } else if (screenWidth >= 992) {
        return 3;
    } else if (screenWidth >= 768) {
        return 2;
    } else {
        return 1;
    }
    }

    return (
    <CenteredContainer>
        <ItemsContainer>
        {streamingList.map((streamingItem) => (
            <div key={streamingItem.id} style={{ flexBasis: `${100 / itemsPerRow}%` }}>
            <StreamingCardComponent
                title={streamingItem.title}
                description={streamingItem.description}
                host_nickname={streamingItem.host_nickname}
                max_participant={streamingItem.max_participant}
                start_time={streamingItem.start_time}
                status={streamingItem.status}
                current_participants={streamingItem.current_participants}
                meeting_id={streamingItem.id}
                recipe_id={streamingItem.recipe_id}
            />
            </div>
        ))}
        </ItemsContainer>
    </CenteredContainer>
    );
}

export default MyStreamings;