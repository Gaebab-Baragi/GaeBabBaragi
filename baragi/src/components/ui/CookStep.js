import React, { useState } from 'react';
import InputCookstep from './InputCookstep'

function CookStep() {
    const [stepCount] = useState(1);
    const [InputCooksteps, setInputCooksteps] = useState([<InputCookstep key={0} step={stepCount} />]);
    
    const handleDeleteInputCookstep = (step) => {
      
      const updatedCooksteps = InputCooksteps.filter((inputCookstep) => inputCookstep.props.step !== step);
      setInputCooksteps(updatedCooksteps);
      // Update the step values of the remaining steps
      const updatedSteps = updatedCooksteps.map((inputCookstep, index) => {
        return React.cloneElement(inputCookstep, { step: index + 1 });
      });
      setInputCooksteps(updatedSteps);
    };
    const handleAddInputCookstep = ()=>{

        setInputCooksteps([...InputCooksteps, <InputCookstep key={InputCooksteps.length} step={InputCooksteps.length + 1} content={InputCooksteps.cookstepValue} onDelete={() => handleDeleteInputCookstep(InputCooksteps.length + 1)} />]);

  }
  return (

    <div>
      <div style={{ display: 'flex', padding: '1%'}}>
        <h4 style = {{textAlign:'left', marginLeft:'2%', marginRight : '15%'}}>4. 요리 순서</h4>
      </div>

      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom:'1%' ,flexDirection: 'column', gap: '10px' }}>
        {InputCooksteps.map((InputCookstep, index) => (
          <div key={index}>{InputCookstep}
            <button onClick={() => handleDeleteInputCookstep(InputCookstep.props.step)}>-</button>
          </div>
        ))}
      </div>
      <button onClick={handleAddInputCookstep}>+</button>
    </div>
  );
}

export default CookStep;