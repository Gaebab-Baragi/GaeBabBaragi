import React, { useState } from 'react';
import InputCookstep from './InputCookstep'

function CookStep() {
    const [stepCount, setStepCount] = useState(1);
    const [InputCooksteps, setInputCooksteps] = useState([]);
    const handleAddInputCookstep = ()=>{
        setInputCooksteps([...InputCooksteps,<InputCookstep key={InputCooksteps.length} step={stepCount} />]); 
        setStepCount(stepCount+1);
  }
  return (

    <div container>
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'left', padding: '10px 0'}}>
        <h4>4. 요리 순서</h4>
      </div>

      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '10px 0' ,flexDirection: 'column', gap: '10px' }}>
        {InputCooksteps.map((InputCookstep, index) => (
          <div key={index}>{InputCookstep}</div>
        ))}
      </div>
      <button onClick={handleAddInputCookstep}>+</button>
    </div>
  );
}

export default CookStep;