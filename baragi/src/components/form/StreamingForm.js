import React, { useEffect, useState } from 'react';
import DateTime from './DateTime'
import LimitForm from './LimitForm';
import { useDispatch } from "react-redux";
import {setTitle, setDescription, requestStreamingReservation} from './../../redux/streamingRegisterSlice.js'

function StreamingForm() {
  const dispatch = useDispatch();
  const [name, setName] = useState('');
  const [room, setRoom] = useState('');

  useEffect(()=>{
    dispatch(setTitle(name))
  }, [name])

  useEffect(()=>{
    dispatch(setDescription(room))
  }, [room])

  const handleNameChange = (e) => {
    setName(e.target.value);
  };

  const handleRoomChange = (e) => {
    setRoom(e.target.value);
  };

  const handleRegistrationSubmit = (e) => {
    e.preventDefault();
    // Perform registration logic (e.g., send data to the server)
    // axios 요청 보내기 
    // Display registration information
    alert(`Thank you for registering!${name},${room}`);
    // \n\nName: ${name}\nEmail: ${room}\n
  };

  const handleRequestStreamingRegister = (e) =>{
    dispatch(requestStreamingReservation())
  }

  return (
    <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', gap: '10px' }}>

      <form onSubmit={handleRegistrationSubmit} style={{marginLeft:'2%', marginRight : '15%', padding:'1%' }}>
        <div style={{ display: 'flex',}}>
          {/* display: 'flex', flexDirection: 'column', maxWidth: '1000px', gap: '15px' , justifyContent: 'space-between'  */}

          <label htmlFor="name">방제목:</label>
          <input
            type="text"
            id="name"
            name="name"
            value={name}
            onChange={handleNameChange}
            placeholder="제목을 입력하세요."
            style={{ width: '800px' }}
          />
        </div>
        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
          <label htmlFor="name">레시피:</label>
          <div>여기에 레시피 알아서 받도록 할 예정</div>
        </div>

        <div style={{ display: 'flex'}}>
            <label htmlFor="time" style={{marginRight: '10px'}}>스트리밍 시작 시간:</label>
            <DateTime></DateTime>
        </div>
{/* 스트리밍 시작 시간 */}
        <div style={{ display: 'flex'}}>
            <label htmlFor="Limit" style={{marginRight: '10px'}}>참가 가능 인원:</label>
            <LimitForm></LimitForm>
        </div>

{/* 참가 가능 인원 */}

        <div style={{ display: 'flex', justifyContent: 'space-between' }}>
          <label htmlFor="room">방소개:</label>
          <input
            type="room"
            id="room"
            name="room"
            value={room}
            onChange={handleRoomChange}
            placeholder="방 소개하세요"
            style={{ width: '800px' }}
          />
        </div>


        <div style={{ display: 'flex', justifyContent: 'flex-end' }}>
          <button onClick={handleRequestStreamingRegister}  type="submit" style={{ width: '100px' }}>예약</button>
        </div>
      </form>
    </div>
  );
}

export default StreamingForm;
