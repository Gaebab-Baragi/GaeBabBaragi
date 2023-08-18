import axios from 'axios';
import { useDispatch } from 'react-redux';
import streamingInfo, { setStreamingInfo } from '../../../redux/streamingInfoSlice';
import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import Card from 'react-bootstrap/Card';
import './RecipeDetailStreamingCard.css'
import Toast from '../Toast';
import PassswordModal from './PasswordModal';
import useDidMountEffect from '../../../useDidMountEffect';

function RecipeDetailStreamingCard({is_private_room, meeting_id, recipe_id, recipe_image_url, current_participants, max_participant, status, host_profile_url, title, host_nickname, start_time}) {
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [modalShow, setModalShow] = useState(false);
    const [enteredPassword, setEnteredPassword] = useState(null);

    // 비밀번호 입력 확인
    useEffect(()=>{
      console.log('비밀번호 입력됨', enteredPassword)
    },[enteredPassword])
    //  비밀번호가 입력되었을 떄
    useDidMountEffect(()=>{
      axios.get(process.env.REACT_APP_BASE_URL +`/api/meetings/join-request/${meeting_id}?password=${enteredPassword}`)
        .then((res)=>{
          console.log('request success : ', res.data);
          const data = {
            meeting_id,
            title,
            recipe_id,
            host_nickname,
            max_participant,
            start_time,
            recipe_image_url,    
            }
            dispatch(setStreamingInfo(data))
            axios.post(process.env.REACT_APP_BASE_URL +`/api/meetings/join/${meeting_id}`)
            .then((res)=>{
                console.log('미팅 참여 성공 ')
                navigate('/streaming-live')
            })
            .catch((err)=>{
              Toast.fire(err.response.data.message,"","warning")
              console.log('error after join accepted', err.message)
            })
        })
        .catch((err)=>{
            Toast.fire(err.response.data.message, "", "warning")
            console.log('error occured' , err.response.data.message)
        })
    },[enteredPassword])

    const handlePasswordEntered = (password) =>{
      setEnteredPassword(password)
      setModalShow(false)
    }

    function checkMeeting() {
        console.log('PRIVATE ROOM :', is_private_room)
        // 비밀번호 요청하기 + 모달창 띄우기
        if (is_private_room) {
            setModalShow(true);
        } else {

            // 비밀번호 없이 미팅 참여
            axios.get(process.env.REACT_APP_BASE_URL +`/api/meetings/join-request/${meeting_id}`)
            .then((res)=>{
              console.log('request success : ', res.data);
              const data = {
                meeting_id,
                title,
                recipe_id,
                host_nickname,
                max_participant,
                start_time,
                recipe_image_url,    
                }
                dispatch(setStreamingInfo(data))
                axios.post(process.env.REACT_APP_BASE_URL +`/api/meetings/join/${meeting_id}`)
                .then((res)=>{
                    console.log('미팅 참여 성공 ')
                    navigate('/streaming-live')
                })
                .catch((err)=>{
                  Toast.fire(err.response.data.message,"","warning")
                  console.log('error after join accepted', err.message)
                })
            })
            .catch((err)=>{
                Toast.fire(err.response.data.message, "", "warning")
                console.log('error occured' , err.response.data.message)
            })
        }
        

    }
    
    useEffect(()=>{
        console.log(meeting_id)
    },[])

    
    
    return (
        <div className='recipe-streaming-card-wrapper'>
          <PassswordModal handlePasswordEntered={handlePasswordEntered} show={modalShow} onHide={()=>setModalShow(false)} />
            <Card className="recipe-streaming-card" onClick={()=>checkMeeting()}>
                
                <Card.Img src={recipe_image_url} alt="레시피 대표 이미지" />
                
            </Card>

            <div className='recipe-floatingDiv-meeting-info'>
                <div className='recipe-floatingDiv-meeting-host'>
                    <img className='recipe-floatingDiv-meeting-host-img' src={host_profile_url}></img>
                    {host_nickname}
                </div>
                • 예약 시간 : {start_time}
            </div>





        </div>
    );
}

export default RecipeDetailStreamingCard;