import axios from 'axios';
import { useDispatch } from 'react-redux';
import { setStreamingInfo } from '../../../redux/streamingInfoSlice';
import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';
import Card from 'react-bootstrap/Card';
import './StreamingCard.css'


function StreamingCardComponent({meeting_id, recipe_image_url, current_participants, max_participant, status, host_profile_url, title, description, host_nickname, start_time}) {
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
                description,
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
        <>
            <Card className="streaming-card">
                <Card.Img src='/image/스트리밍 썸네일 배경.png' alt="스트리밍 썸네일 배경" className='card-img-bg'/>
                <Card.Img src={recipe_image_url} alt="레시피 대표 이미지" />
                <Card.ImgOverlay>
                    <ion-icon name="play-circle-outline"></ion-icon>
                    <Card.Text>
                        
                    </Card.Text>
                </Card.ImgOverlay>
            </Card>
        </>
    );
}

export default StreamingCardComponent;