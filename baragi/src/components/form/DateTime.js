import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { setStartTime } from '../../redux/streamingRegisterSlice';
import useDidMountEffect from '../../useDidMountEffect';
import { useEffect } from 'react';

function DateTime() {
  const dispatch = useDispatch();
  const [selectedDate, setSelectedDate] = useState('');
  const [selectedTime, setSelectedTime] = useState('');


  useEffect(()=>{
    dispatch(setStartTime(selectedDate +' '+ selectedTime))
  }, [selectedDate, selectedTime])


  const handleDateChange = (e) => {
    setSelectedDate(e.target.value);
  };

  const handleTimeChange = (e) => {
    setSelectedTime(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // 여기서 입력된 날짜와 시간을 서버로 전송하거나 다른 작업을 수행할 수 있습니다.
    console.log('Submitted Data:', {
      date: selectedDate,
      time: selectedTime,
    });
  };

  return (
    <form onSubmit={handleSubmit}>
      <div style={{ display: 'flex', justifyContent: 'space-between' }}>
        <label htmlFor="date" style={{ marginRight: '10px' }}>Date:</label>
        <input
          type="date"
          id="date"
          value={selectedDate}
          onChange={handleDateChange}
        />
        <label htmlFor="time" style={{ marginLeft: '10px' }}>Time:</label>
        <input
          type="time"
          id="time"
          value={selectedTime}
          onChange={handleTimeChange}
        />
      </div>


      {/* <button type="submit">Submit</button> */}
    </form>
  );
}

export default DateTime;
