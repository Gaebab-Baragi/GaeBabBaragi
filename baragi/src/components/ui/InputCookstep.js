import React, { useState, useRef } from 'react';
// import InputImage from './InputImage';
import '../../pages/StreamingRegisterPage.css'
import {updateStepImage} from '../../redux/recipeRegisterSlice'
import { useDispatch } from 'react-redux';



function InputCookstep({ step, description, onCookstepChange, onDelete , onStepImageChange}) {
  const dispatch = useDispatch()
  
  const handleCookstepChange = (e) => {
    const newValue = e.target.value;
    onCookstepChange(step, newValue);
  };

  const defaultImageUrl = './기본이미지.PNG';
  const [image, setImage] = useState(defaultImageUrl);
  const [file, setFile] = useState("");
  const fileInputRef = useRef(null);

  const handleStepImage = (e, step) => {
    const selectedImage = e.target.files[0];
    console.log('handlestep임', step,selectedImage)
    const context = {
      'step' : step,
      'selectedImage' : selectedImage
    } 
    dispatch(updateStepImage(context));
    // dispatch(updateStepImage(selectedImage,step));
    // setFile(selectedImage);
    // if (selectedImage) {
    //   const reader = new FileReader();
    //   reader.onloadend = () => {
    //     setImage(reader.result);
    //   };
    //   console.log(file);
    //   reader.readAsDataURL(selectedImage);
    
  }


  return (
    <>
      <div style={{ display: 'flex', marginLeft : '2%',  marginRgiht:'2%' }}>
        <label style={{ width : '15%' }} htmlFor={`Cookstep${step}`}>{`STEP${step}:`}</label>
        <textarea
          rows="5"
          cols="30"
          id={`Cookstep${step}`}
          name={`Cookstep${step}`}
          value={ description }
          onChange={handleCookstepChange}
          placeholder="예)요리방법 돼지고기 소고기 요리해줘"
          style = {{ flex : 1, 
          marginRight : '3%',   
          borderRadius: '10px',
          border: '2px solid #000',
          background: '#FFF',
          // width: '32vw',
          // height: '40px',
          flexShrink: 0,
          fontSize: '1vw',
          fontWeight: 700,
          textIndent: '10px'}}
        />
      <input type="file" accept="" onChange={(e) => handleStepImage(e, step)}/>
      </div>
      <button onClick={onDelete}>-</button>
    </>
  );
}

export default InputCookstep;
