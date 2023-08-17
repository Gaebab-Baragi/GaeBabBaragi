/* eslint-disable */
import { useEffect, useState } from "react";
import axios from "axios";
import "./css/StreamingRegisterPage.css"
import { useSelector } from "react-redux";
import { redirect, useNavigate } from "react-router-dom";
import { useParams } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
import Confirm from '../../src/components/ui/Confirm';
import Toast from '../../src/components/ui/Toast';

function StreamingRegisterPage() {
  const location = useLocation();
  const { id } = useParams();
  const { recipeTitle } = location.state;
  const [roomTitle, setRoomTitle] = useState("");
  const [roomDescription, setRoomDescription] = useState("");
  const [selectedDate, setSelectedDate] = useState("");
  const [selectedTime, setSelectedTime] = useState("");
  const [maxParticipant, setMaxParticipant] = useState(2);
  const user = useSelector(state=>state.user)
  const navigate = useNavigate();
  const [isPrivate, setIsPrivate] = useState(false); // 비밀방 여부 상태 추가
  const [password, setPassword] = useState(null); // 비밀번호 상태 추가


  // 로그인 안된 유저는 접근 안됨
  useEffect(()=>{
    if (!user) {
      Confirm().then(() => {
        // Handle anything else after confirmation if needed
      });
    }
  },[])
  const handlePasswordChange = (e) => {
    const newPassword = e.target.value;
  
    if (newPassword.length <= 6) {
      setPassword(newPassword);
    }
  };
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
    // let tmpPw = password
    // if (!password) {
    //   tmpPw=password.toString()
    // }
  
    const data = {
      title: roomTitle,
      description: roomDescription,
      password: password,
      max_participant: maxParticipant,
      start_time: startTime,
      recipe_id: parseInt(id),
    };

    if(!(data.title).trim() || !(data.description).trim() || !(data.start_time).trim() ||!selectedTime || !selectedDate){
      
      return Toast.fire("내용을 입력해주세요","","warning");
    }
    if(isPrivate && data.password==null){
      return Toast.fire("비밀번호를 확인해주세요.","","warning");
    }
    if(isPrivate && data.password && data.password.length<6){
      return Toast.fire("비밀번호를 확인해주세요.","","warning");
    }

    axios
      .post(process.env.REACT_APP_BASE_URL +"/api/meetings", data)
      .then((response) => {
        // Handle the response if needed
        Toast.fire("스트리밍 생성이 완료되었습니다.", "", "success")
        
        navigate('/streaming-list');
      })
      .catch((error) => {
        // Handle errors if necessary
        Toast.fire(error.response.data,"","warning")
        console.error("Error sending request:", error.response);
        navigate('/'); // 메인 페이지로 리다이렉트
        return; // 리다이렉트 후 함수 종료
      });
    };

    return (
    <div className="StreamingRegisterContainer">
      <div className="StreamingHeader">
        <h2 className="StreamingRegisterTitle">스트리밍 예약하기</h2>
      </div>
      <div className="input-list">
        <div className="input-info">
          <div className="input-details">
            <div className="input-property">레시피</div>
            <div className="input-content">{recipeTitle}</div>
          </div>
        </div>
      </div>
      <div className="input-list">
        <div className="input-info">
          <div className="input-details">
            <div className="input-property">스트리밍 제목</div>
            <div className="input-content">
              <input
                onChange={(e) => setRoomTitle(e.target.value)}
                type="text"
                placeholder="스트리밍 제목을 입력하세요."
                className="roomName"
              />
            </div>
          </div>
        </div>
      </div>
      <div className="input-list">
        <div className="input-info">
          <div className="input-details">
            <div className="input-property">스트리밍 소개</div>
            <div className="input-content">
              <textarea
                onChange={(e) => {
                  setRoomDescription(e.target.value);
                }}
                className="description"
              />
            </div>
          </div>
        </div>
      </div>
      <div className="input-list">
        <div className="input-info">
          <div className="input-details">
            <div className="input-property">스트리밍 시작 시간</div>
            <div className="input-content">
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
          </div>
        </div>
      </div>
      <div className="input-list">
        <div className="input-info">
          <div className="input-details">
            <div className="input-property">참가 가능 인원</div>
            <div className="input-content">
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
          </div>
        </div>
      </div>

      <div className="input-list">
        <div className="input-info">
          <div className="input-details">
            <div className="input-property">비밀번호 설정</div>
            <div className="input-content">
              <input
                type="password"
                value={password}
                onChange={handlePasswordChange}
                disabled={!isPrivate}
                className="password"
                style={{ backgroundColor: isPrivate ? '#FFF' : '#f0f0f0' }}
              />
              <div className="input-content">
                <input
                  className="checkbox"
                  type="checkbox"
                  checked={isPrivate}
                  onChange={(e) => {
                    setIsPrivate(e.target.checked);
                    if (!e.target.checked) {
                      setPassword(null);
                    }
                  }}
                />
                비밀 스트리밍 설정
              </div>
            </div>

          </div>
        </div>
      </div>
      <div className="input-list" style={{'margin-top':0}}>
        <div className="input-info">
          <div className="input-details">
            <div className='input-property'>

            </div>
            <div className='input-content'>
            {isPrivate && password && password.length < 6 && (
                <div className="password-notice">비밀번호는 숫자 6자리로 설정해주세요.</div>
              )}
            </div>
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
