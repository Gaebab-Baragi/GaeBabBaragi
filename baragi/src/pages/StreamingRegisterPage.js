/* eslint-disable */
import { useEffect, useState } from "react";
import axios from "axios";
import "./css/StreamingRegisterPage.css"

import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { useParams } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
function StreamingRegisterPage() {
  const location = useLocation();
  const { id } = useParams();
  const { recipeTitle } = location.state;
  const [roomTitle, setRoomTitle] = useState("");
  const [roomDescription, setRoomDescription] = useState("");
  const [selectedDate, setSelectedDate] = useState("");
  const [selectedTime, setSelectedTime] = useState("");
  const [maxParticipant, setMaxParticipant] = useState(2);
  const [password, setPassword] = useState("");
  const user = useSelector(state=>state.user)
  const navigate = useNavigate();
  // 로그인 안된 유저는 접근 안됨
  useEffect(()=>{
    if (!user) {
      alert('로그인 후 이용해주세요.')
      navigate('/login')
    }
  },[])

  const handleIncrease = (e) => {
    e.preventDefault();
    if (maxParticipant < 5) {
      setMaxParticipant(maxParticipant + 1);
    }
  };

  const handleDecrease = (e) => {
    e.preventDefault();
    if (maxParticipant > 2) {
      setMaxParticipant(maxParticipant - 1);
    }
  };
  // =====================제출======================//
  const handleRegisterSubmit = ()=>{
    const startTime = selectedDate + ' ' + selectedTime
    console.log('password', password, typeof(password))
  
    const data = {
      title: roomTitle,
      description: roomDescription,
      password: password.toString(),
      max_participant: maxParticipant,
      start_time: startTime,
      recipe_id: parseInt(id),
    };
    console.log(data);

    axios
      .post(process.env.REACT_APP_BASE_URL +"/api/meetings", data)
      .then((response) => {
        // Handle the response if needed
        alert('스트리밍 예약이 완료되었습니다.')
        console.log("Request successful:", response.data);
      })
      .catch((error) => {
        // Handle errors if necessary
        alert('빈 칸을 채워주세요.')
        console.error("Error sending request:", error.response);
      });
  };
  return (

    
    <div className="StreamingRegisterContainer">
      <div className="StreamingHeader">
      <h2 className="StreamingRegisterTitle">스트리밍 예약하기</h2>
      <h4 className="StreamingRegisterSemiTitle">스트리밍 기본 정보 입력</h4>
      </div>
      <div className="inputContainer">
        <div className="inputContainer-left">
          <label>방 제목 </label>
          <label>레시피 </label>
          <label>스트리밍 시작 시간 </label>
          <label>참가 가능 인원 </label>
          <label>방 비밀번호 설정 </label>
          <label>방 소개 </label>
        </div>
        <div className="inputContainer-right">
          {/* 방제목 */}
          <div className="inputComponent">
            <input
              onChange={(e) => setRoomTitle(e.target.value)}
              type="text"
              placeholder="방 제목을 입력하세요."
              className="roomName"
            />
          </div>
          {/* 레시피 */}
          <div className="inputComponent">
            <span className="recipeName">{recipeTitle}</span>
          </div>
          {/* 스트리밍 시작 시간 */}
          <div className="inputComponent">
            <input
              type="date"
              onChange={(e) => setSelectedDate(e.target.value)}
              className="startTime"
            />
            <input
              type="time"
              onChange={(e) => setSelectedTime(e.target.value)}
              className="startTime"
            />
          </div>
          {/* 참가 가능 인원 */}
          <div className="inputComponent">
            <button
              onClick={handleDecrease}
              disabled={maxParticipant === 1}
              className="minusBtn"
            >
              -
            </button>
            <span className="maxParticipant">{maxParticipant}</span>
            <button
              onClick={handleIncrease}
              disabled={maxParticipant === 5}
              className="plusBtn"
            >
              +
            </button>
          </div>
          {/* 방 비밀번호 설정 */}
          <div className="inputComponent">
            <input
              type="password"
              onChange={(e) => {
                setPassword(e.target.value);
              }}
              className="password"
            />
          </div>
          {/* 방 소개 */}
          <div className="descriptionComponent">
            <input
              onChange={(e) => {
                setRoomDescription(e.target.value);
              }}
              type="text"
              className="description"
            />
          </div>
        </div>
      </div>
      <div className="inputContainer-bottom">
        <button onClick={handleRegisterSubmit} className="registerBtn">
          예약
        </button>
      </div>
    </div>
  );
}
export default StreamingRegisterPage;
