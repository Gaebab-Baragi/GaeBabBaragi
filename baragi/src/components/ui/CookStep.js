import React, { useState, useEffect } from 'react';
import InputCookstep from './InputCookstep';
import { useDispatch, useSelector } from 'react-redux';
import { requestFilteredRecipeList,addStepImage, updateStep,updateStepImage,deletedStepImage} from '../../redux/recipeRegisterSlice';
import useDidMountEffect from '../../useDidMountEffect'

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
    dispatch(deletedStepImage(step))
  };

  const handleAddInputCookstep = () => {
    const nextStepCount = inputCooksteps.length + 1;
    setStepCount(nextStepCount);
    setInputCooksteps([...inputCooksteps, { orderingNumber: nextStepCount, description: '' }]);
    dispatch(addStepImage())

  };


  // const handleCookstepImage = (step, selectedImage) =>{
  //   console.log('Cookstep에서', step, selectedImage);
  //   dispatch(updateStepImage(step,selectedImage));
  // }

  useDidMountEffect(() => {
    dispatch(updateStep(inputCooksteps));
    // InputCooksteps가 변경될 때마다 실행되는 함수
    // console.log('InputCooksteps가 변경되었습니다:', inputCooksteps);
    // 원하는 작업을 수행할 수 있습니다.
  }, [inputCooksteps]); // inputCooksteps가 변경될 때마다 실행됩니다.

  return (
    <div>
      <div style={{ display: 'flex', marginBottom: '1%', flexDirection: 'column', gap: '10px' , background : '#0001'}}>
        {inputCooksteps.map((inputCookstep, index) => (
          <div key={index}>
            <InputCookstep
              step={inputCookstep.orderingNumber}
              description={inputCookstep.description}
              onCookstepChange={handleCookstepChange}
              // onStepImageChange ={handleCookstepImage}
              // onStepImageChange={(step, selectedImage) => handleCookstepImage(step, selectedImage)}
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
