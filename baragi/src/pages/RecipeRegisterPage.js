import React from 'react';
import InputImage from '../components/ui/InputImage';
import InputInfor from '../components/ui/InputInfor';
import MaterialRegist from '../components/ui/MaterialRegist';
import CookStep from '../components/ui/CookStep';
import { useDispatch } from 'react-redux';
import { requestFilteredRecipeList,updateVideo} from '../redux/recipeRegisterSlice';
import  { useState } from 'react';
import {useNavigate} from 'react-router-dom'


function RecipeRegisterPage() {
  const navigator = useNavigate()
  const dispatch = useDispatch();
  const [video, setVideo] = useState('./기본이미지.PNG');
  const [file, setFile] = useState("");
  
  const handlerequest = ()=>{
    dispatch(requestFilteredRecipeList())
    
    navigator('/recipe-list')
  }

  const handleVideoChange = (e)=>{
    const selectedVideo = e.target.files[0];
    dispatch(updateVideo(selectedVideo));
    setFile(selectedVideo);
    if (selectedVideo) { 
      const reader = new FileReader();
      reader.onloadend = () => {
        setVideo(reader.result);
      };
      reader.readAsDataURL(selectedVideo);

    }
  }

 
  // const handleImageUpload = (imageData) => {
  //   console.log('리덕스에 저장되냐?',imageData)
  //   dispatch(updateImage(imageData));
  // };

  return (
    <>
    <div style={{ marginLeft:'15%' , marginRight : '15%' , marginTop : '0.5%' , marginBottom : '10%'}}>
        <h1 style={{ textAlign : 'left',marginBottom :'1%' }}>레시피 등록</h1>
        <h4 style={{textAlign:'left', marginLeft:'5%'}}>1. 대표사진 등록</h4>
        <div style={{ marginTop : '1%', marginBottom : '1%' ,  justifyContent:'center', alignItems:'center'}}> 
        {/* backgroundColor : '#0001', */}
          <InputImage></InputImage>
        </div>
        <h4 style={{ textAlign: 'left', marginLeft:'5%' }}>2. 기본 정보 입력</h4>
        <InputInfor></InputInfor>
        <h4 style={{textAlign:'left', marginLeft:'5%'}}>3. 재료 등록</h4>
        <MaterialRegist></MaterialRegist>
        <h4 style={{ textAlign: 'left', marginLeft:'5%' }}>4. 요리 순서</h4>
        <CookStep></CookStep>
        <h4 style={{ textAlign: 'left',  marginLeft:'5%' }}>5. 동영상 제출</h4>
        <input type="file" accept="" onChange={handleVideoChange}/>

        <div style={{ marginTop : '1%', marginBottom : '1%' , justifyContent:'center', alignItems:'center'}}>
        <button style ={{ 
          width: '100px',
          height: '40px',
          marginTop: '30px',
          borderRadius: '10px',
          backgroundColor: '#ffaa00',
          color: 'white',
          border: 'none',
          fontWeight: '500'}}
          onClick={handlerequest}>제출</button>

        </div>
    </div>
    </>
  );
}

export default RecipeRegisterPage;






