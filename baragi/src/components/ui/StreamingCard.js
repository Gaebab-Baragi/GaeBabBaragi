import Card from 'react-bootstrap/Card';
import recipeImg from './recipeImg.png'
import './StreamingCard.css'
import axios from 'axios';
import { useDispatch } from 'react-redux';
import { setStreamingInfo } from '../../redux/streamingInfoSlice';
import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';

function StreamingCardComponent({title,description,host_nickname,max_participant,start_time,status,current_participants,recipe_id,meeting_id}) {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  function checkMeeting() {
    console.log(meeting_id)
    axios.get(`http://localhost:8083/api/meetings/join-request/${meeting_id}`)
    .then((res)=>{
      console.log('request success : ', res.data);
        const data = {
          meeting_id: meeting_id,
          title : title,
          recipe_id: recipe_id,
          host_nickname: host_nickname,
          max_participant: max_participant,
          start_time:start_time
        }
        dispatch(setStreamingInfo(data))
        axios.post(`http://localhost:8083/api/meetings/join/${meeting_id}`)
        .then((res)=>{
          console.log('미팅 참여 성공 ')
        })
        // window.open('http://localhost:3000/streaming-live','_blank')
        navigate('/streaming-live')
        
      })
    .catch((err)=>{
      console.log('error occured' + err)
    })
    }
    
    useEffect(()=>{
      console.log(meeting_id)
    },[])
  
  return (
    <Card style={{ width: '22rem'}}>
      <Card.Img
        variant="top"
        src={recipeImg}
      />
      <div className='card-img-overlay' style={{color:'white',marginTop:'80px'}}>
        <ion-icon onClick={()=>checkMeeting()} size="large" name="play-circle-outline"></ion-icon>
      </div>
      <div className='CardBody'>
        {/* 제목 */}
        <div className='cardBodyComponent'>
          <h4 className='bodyComponent'>{title}</h4>
        </div>
        {/* 설명 */}
        <div className="cardBodyComponent">
          <p className='bodyComponent'>{description}</p>
        </div>
        {/* 방송날짜 */}
        <div className="cardBodyComponent">
          <p className='bodyComponent'> 방송 날짜 : {start_time} </p>
        </div>
        {/* 호스트 */}
        <div className="cardBodyComponent">
          <p className='bodyComponent'> 호스트 : {host_nickname}</p>
        </div>
        {/* 인원 수 */}
        <div className='cardBodyComponent'>
          <p className='bodyComponent'>인원 수 : {current_participants} / {max_participant} </p>
        </div>
      </div>
    </Card>
  );
}

export default StreamingCardComponent;