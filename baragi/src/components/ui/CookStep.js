import React, { useState, useEffect } from 'react';
import InputCookstep from './InputCookstep';
import { useDispatch, useSelector } from 'react-redux';
import { requestFilteredRecipeList, updateStep } from '../../redux/recipeRegisterSlice';

function CookStep() {
  const dispatch = useDispatch();

  const [stepCount, setStepCount] = useState(1);
  const [inputCooksteps, setInputCooksteps] = useState([{ orderingNumber: 1, description: '' }]);

  const handleCookstepChange = (step, newValue) => {
    setInputCooksteps((prevInputCooksteps) =>
      prevInputCooksteps.map((inputCookstep) =>
        inputCookstep.orderingNumber === step ? { ...inputCookstep, description: newValue } : inputCookstep
      )
    );
  };

  const handleDeleteInputCookstep = (step) => {
    setInputCooksteps((prevInputCooksteps) => {
      const updatedCooksteps = prevInputCooksteps.filter((inputCookstep) => inputCookstep.orderingNumber !== step);
      return updatedCooksteps.map((inputCookstep, index) => ({
        ...inputCookstep,
        orderingNumber: index + 1,
      }));
    });
  };

  const handleAddInputCookstep = () => {
    const nextStepCount = inputCooksteps.length + 1;
    setStepCount(nextStepCount);
    setInputCooksteps([...inputCooksteps, { orderingNumber: nextStepCount, description: '' }]);
  };

  useEffect(() => {
    dispatch(updateStep(inputCooksteps));
    // InputCooksteps가 변경될 때마다 실행되는 함수
    // console.log('InputCooksteps가 변경되었습니다:', inputCooksteps);
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
              step={inputCookstep.orderingNumber}
              description={inputCookstep.description}
              onCookstepChange={handleCookstepChange}
              onDelete={() => handleDeleteInputCookstep(inputCookstep.orderingNumber)}
            />
          </div>
        ))}
      </div>
      <button onClick={handleAddInputCookstep}>+</button>
    </div>
  );
}

export default CookStep;
