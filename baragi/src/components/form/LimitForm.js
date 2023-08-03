import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useEffect } from 'react';
import { setMaxParticipant } from '../../redux/streamingRegisterSlice';

function LimitForm() {
  const dispatch = useDispatch();
  const [number, setNumber] = useState(1); // Initialize the number to 1

  useEffect(()=>{
    dispatch(setMaxParticipant(number))
  }, [number])

  const handleIncrease = (e) => {
    e.preventDefault();
    if (number < 5) {
      setNumber(number + 1);
    }
  };

  const handleDecrease = (e) => {
    e.preventDefault();
    if (number > 1) {
      setNumber(number - 1);
    }
  };

  return (
    <div style={{ display: 'flex' }}>
      <button onClick={handleDecrease} disabled={number === 1}>
        -
      </button>
      <h4 style={{ marginLeft: '10px', marginRight: '10px' }}>{number}</h4>
      <button onClick={handleIncrease} disabled={number === 5}>
        +
      </button>
    </div>
  );
}

export default LimitForm;
