import axios from "axios";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from 'react-router-dom';
import { useDispatch } from "react-redux";
import { setStreamingInfo } from "../redux/streamingInfoSlice";
import StreamingCardComponent from "../components/ui/StreamingCard";

function StreamingListPage() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [streamingList, setStreamingList] = useState([]);
  const user = useSelector((state) => (state.user));
  const nickname = <user className="nickname"></user>;

  useEffect(() => {
    axios.get('/api/meetings')
      .then((res) => {
        console.log('get list is successful : ', res.data);
        setStreamingList(res.data);
      })
      .catch((err) => {
        console.log('error : ', err);
      });
  }, []);

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
    <div>
      <div style={{ display: 'flex', flexWrap: 'wrap', gap: '20px' }}>
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
      </div>
    </div>
  );
}

export default StreamingListPage;
