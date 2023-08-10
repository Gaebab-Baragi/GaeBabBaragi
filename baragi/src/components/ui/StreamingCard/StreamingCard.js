import axios from 'axios';
import { useDispatch } from 'react-redux';
import { setStreamingInfo } from '../../../redux/streamingInfoSlice';
import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';
import Card from 'react-bootstrap/Card';
import './StreamingCard.css'


function StreamingCardComponent({meeting_id, recipe_image_url, current_participants, max_participant, status, host_profile_url, title, host_nickname, start_time}) {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    function checkMeeting() {
        console.log(meeting_id)
        axios.get(`http://localhost:8083/api/meetings/join-request/${meeting_id}`)
        .then((res)=>{
            console.log('request success : ', res.data);
            const data = {
                recipe_image_url,
                current_participants,
                max_participant,
                status,
                host_profile_url,
                title,
                host_nickname,
                start_time
            }
            dispatch(setStreamingInfo(data))
            axios.post(`http://localhost:8083/api/meetings/join/${meeting_id}`)
            .then((res)=>{
                console.log('미팅 참여 성공 ')
            })
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
        <div className='streaming-card-wrapper'>
            <Card className="streaming-card" onClick={()=>checkMeeting()}>
                <Card.Img src='/image/스트리밍 썸네일 배경.png' alt="스트리밍 썸네일 배경" className='card-img-bg'/>
                <Card.Img src={recipe_image_url} alt="레시피 대표 이미지" />
                <Card.ImgOverlay className='overlay-icon'>
                    <ion-icon name="play-circle-outline"></ion-icon>
                </Card.ImgOverlay>
                    {
                        status === "ATTENDEE_WAIT" && (
                            <Card.ImgOverlay className='overlay-participants'>
                                <div className='participants'>
                                    <ion-icon name="person-sharp"></ion-icon>
                                    <pre> </pre>
                                    <p>{current_participants} / {max_participant}</p>
                                </div>
                            </Card.ImgOverlay>
                        )
                    }
                    {
                        status == 'ATTENDEE_WAIT' ? (
                            <Card.ImgOverlay className='overlay-wait'>
                                <div className='status-attendee-wait'>
                                    <p>입장 가능</p>
                                </div>
                            </Card.ImgOverlay>
                        )
                        : (
                            <Card.ImgOverlay className='overlay-scheduled'>
                                <div className='status-scheduled'>
                                    <p>예정</p>
                                </div>
                            </Card.ImgOverlay>
                        )
                    }
            </Card>
            <div className='streaming-info row'>
                <div className='streaming-host-profile col-2'>
                    <img src={host_profile_url}></img>
                </div>
                <div className='streaming-info-detail col-10'>
                    <p className='streaming-title'>{title}</p>
                    <p className='streaming-host-nickname-and-start-time'>{host_nickname} • 예약 시간:{start_time}</p>
                </div>
            </div>
        </div>
    );
}

export default StreamingCardComponent;