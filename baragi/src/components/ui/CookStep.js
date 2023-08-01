import React, { useState, useEffect } from 'react';
import InputCookstep from './InputCookstep';
import { useDispatch, useSelector } from 'react-redux';


function CookStep() {
  const dispatch = useDispatch();
  

  const [stepCount, setStepCount] = useState(1);
  const [inputCooksteps, setInputCooksteps] = useState([{ step: 1, cookstepValue: '' }]);

  const handleCookstepChange = (step, newValue) => {
    setInputCooksteps((prevInputCooksteps) =>
      prevInputCooksteps.map((inputCookstep) =>
        inputCookstep.step === step ? { ...inputCookstep, cookstepValue: newValue } : inputCookstep
      )
    );
  };

  const handleDeleteInputCookstep = (step) => {
    setInputCooksteps((prevInputCooksteps) => {
      const updatedCooksteps = prevInputCooksteps.filter((inputCookstep) => inputCookstep.step !== step);
      return updatedCooksteps.map((inputCookstep, index) => ({
        ...inputCookstep,
        step: index + 1,
      }));
    });
  };
  const handleAddInputCookstep = () => {
    const nextStepCount = inputCooksteps.length + 1;
    setStepCount(nextStepCount);
    setInputCooksteps([...inputCooksteps, { step: nextStepCount, cookstepValue: '' }]);
  };

  useEffect(() => {
    // InputCooksteps가 변경될 때마다 실행되는 함수
    console.log('InputCooksteps가 변경되었습니다:', inputCooksteps);
    // 원하는 작업을 수행할 수 있습니다.
  }, [inputCooksteps]); // inputCooksteps가 변경될 때마다 실행됩니다.


  
  return (
    <div>
      <div style={{ display: 'flex', padding: '1%' }}>
        <h4 style={{ textAlign: 'left', marginLeft: '2%', marginRight: '15%' }}>4. 요리 순서</h4>
      </div>

      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'space-between', marginBottom: '1%', flexDirection: 'column', gap: '10px' }}>
        {inputCooksteps.map((inputCookstep, index) => (
          <div key={index}>
            <InputCookstep
              step={inputCookstep.step}
              cookstepValue={inputCookstep.cookstepValue}
              onCookstepChange={handleCookstepChange}
            />
            <button onClick={() => handleDeleteInputCookstep(inputCookstep.step)}>-</button>
          </div>
        ))}
      </div>
      <button onClick={handleAddInputCookstep}>+</button>
    </div>
  );
}

export default CookStep;
