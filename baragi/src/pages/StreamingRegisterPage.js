/* eslint-disable */
import StreamingForm from "../components/form/StreamingForm";
import { useEffect, useState } from "react";
import axios from "axios";
import "./StreamingRegisterPage.css";
function StreamingRegisterPage() {
  const [roomTitle, setRoomTitle] = useState("");
  const [roomDescription, setRoomDescription] = useState("");
  const [selectedDate, setSelectedDate] = useState("");
  const [selectedTime, setSelectedTime] = useState("");
  const [maxParticipant, setMaxParticipant] = useState(1);
  const [password, setPassword] = useState("");
  const handleIncrease = (e) => {
    e.preventDefault();
    if (maxParticipant < 5) {
      setMaxParticipant(maxParticipant + 1);
    }
  };

  const handleDecrease = (e) => {
    e.preventDefault();
    if (maxParticipant > 1) {
      setMaxParticipant(maxParticipant - 1);
    }
  };

  const handleRegisterSubmit = ()=>{
    const startDateTime = new Date(selectedDate + 'T' + selectedTime + ':00');
    const isoStartDateTime = startDateTime.toISOString();
    const data = {
      title: roomTitle,
      description: roomDescription,
      password: password,
      max_participant: maxParticipant,
      start_time: isoStartDateTime,
      // 추후 수정!!!!!!!!!!!!!!
      recipe_id: 1,
    };
    console.log(data);

    axios
      .post("http://localhost:8083/api/meetings", data)
      .then((response) => {
        // Handle the response if needed
        console.log("Request successful:", response.data);
      })
      .catch((error) => {
        // Handle errors if necessary
        console.error("Error sending request:", error);
      });
  };

  return (

    
    <div className="StreamingRegisterContainer">
      <StreamingForm/>
      <h2 className="StreamingRegisterTitle">스트리밍 예약하기</h2>
      <h4 className="StreamingRegisterSemiTitle">스트리밍 기본 정보 입력</h4>
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
            <span className="recipeName">가져오기!!</span>
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
        <button onClick={console.log(1)} className="registerBtn">
          예약
        </button>
      </div>
    </div>
  );
}
export default StreamingRegisterPage;
