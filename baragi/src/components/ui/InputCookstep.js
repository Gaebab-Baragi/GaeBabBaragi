import React, { useState, useRef } from 'react';
// import InputImage from './InputImage';
import '../../pages/css/StreamingRegisterPage.css'
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
    if (selectedImage) {
      const reader = new FileReader();
      reader.onload = () => {
        setImage(reader.result);
      };
      reader.readAsDataURL(selectedImage);
    }
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
  };
  const handleButtonClick = () => {
    // Trigger the file input element click when the button is clicked
    fileInputRef.current.click();
  };


  return (
    <>
      <div style={{ display: 'flex', marginLeft : '3%',  marginRgiht:'2%' }}>
        <div style ={{width : '90%', display : 'flex'}}>
          <div style = {{ display : 'flex', flexDirection: 'column'}}> 
            <label style={{ width : '20%' }} htmlFor={`Cookstep${step}`}>{`STEP${step}:`}</label>
            <button style = {{fontSize : '8px'}} onClick={handleButtonClick}>이미지선택</button>
          </div>
        <input ref={fileInputRef} style={{display:'none'}} type="file" accept="" onChange={(e) => handleStepImage(e, step)}/>
        {image && <img src={image} alt={`Step ${step} Preview`} style={{ marginLeft:'2%' ,marginRight : '2%', maxWidth: '10%', height: 'auto' }} />}
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
        </div>
       <button style = {{ margin : 'auto'}}onClick={onDelete}>-</button>
      </div>
     
    </>
  );
}

export default InputCookstep;
