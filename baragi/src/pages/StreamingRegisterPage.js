/* eslint-disable */
import StreamingForm from "../components/form/StreamingForm";
import { useEffect, useState } from "react";
import axios from "axios";

function StreamingRegisterPage() {
  const [roomTitle, setRoomTitle] = useState('');
  const [roomDescription, setRoomDescription] = useState('');
  const [selectedDate, setSelectedDate] = useState('');
  const [selectedTime, setSelectedTime] = useState('');
  const [maxParticipant, setMaxParticipant] = useState(1); 
  const [password, setPassword] = useState('');
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
    const data = {
      title: roomTitle,
      description : roomDescription,
      password: password,
      max_participant: maxParticipant,
      start_time: selectedDate+' '+selectedTime,
      // 추후 수정!!!!!!!!!!!!!!
      recipe_id: 1,
    };
    console.log(data)

    axios.post("http://localhost:8083/api/meetings", data)
      .then((response) => {
        // Handle the response if needed
        console.log("Request successful:", response.data);
      })
      .catch((error) => {
        // Handle errors if necessary
        console.error("Error sending request:", error);
      });
  }
  

  return (

    <div className="StreamingRegisterContainer">
      <h2 className="StreamingRegisterTitle">스트리밍 예약하기</h2>
      <h4 className="StreamingRegisterSemiTitle">스트리밍 기본 정보 입력</h4>
      <div className="inputContainer">
        {/* 레시피 */}
        <div className="inputComponent">
          <label>레시피 제목 : </label>
          <span>가져오기!!</span>
        </div>
        {/* 방제목 */}
        <div className="inputComponent">
          <label>방 제목 : </label>
          <input onChange={(e)=>setRoomTitle(e.target.value)} type="text" placeholder="방 제목을 입력하세요." />
        </div>
        {/* 방 소개 */}
        <div className="inputComponent">
          <label>방 소개 : </label>
          <input onChange={(e)=>{setRoomDescription(e.target.value)}} type="text"/>
        </div>
        {/* 스트리밍 시작 시간 */}
        <div className="inputComponent">
          <label>스트리밍 시작 시간 : </label>
          <input type="date" onChange={(e)=>setSelectedDate(e.target.value)}/>
          <input type="time" onChange={(e)=>setSelectedTime(e.target.value)} />
        </div>
        {/* 참가 가능 인원 */}
        <div className="inputComponent">
          <label>참가 가능 인원 : </label>
          <button onClick={handleDecrease} disabled={maxParticipant === 1}>-</button>
          <span>{maxParticipant}</span>
          <button onClick={handleIncrease} disabled={maxParticipant === 5}>+</button>
        </div>
        {/* 방 비밀번호 설정 */}
        <div className="inputComponent">
          <label>방 비밀번호 설정 : </label>
          <input type="text" onChange={(e)=>{setPassword(e.target.value)}}/>
        </div>
      </div>
      <button onClick={handleRegisterSubmit}>예약하기</button>
    </div>

    );
    }
    export default StreamingRegisterPage;