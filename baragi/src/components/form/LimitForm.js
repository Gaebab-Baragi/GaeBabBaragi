import React, { useState } from 'react';

function LimitForm() {
  const [number, setNumber] = useState(0);

  const handleIncrease = (e) => {
    e.preventDefault()
    setNumber(number + 1);
  };

  const handleDecrease = (e) => {
    e.preventDefault()
    setNumber(number - 1);
  };

  return (
    <div style={{display:'flex'}}>
      <button onClick={handleDecrease}>-</button>
      <h4 style={{marginLeft:'10px', marginRight:'10px'}}>{number}</h4>
      <button onClick={handleIncrease}>+</button>
    </div>
  );
}

export default LimitForm;
