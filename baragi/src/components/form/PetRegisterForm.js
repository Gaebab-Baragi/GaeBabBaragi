import { ReactTags } from "react-tag-autocomplete";
import React, {useCallback, useEffect, useState,useRef} from "react";
import axios from 'axios';
import './PetRegisterForm.css'
import PetIngredientTagBar from "../ui/PetIngredientTagBar";

function PetRegisterForm({pet}) {
  
  const defaultImageUrl = './기본이미지.PNG';
  const [image, setImage] = useState(defaultImageUrl);
  const [file, setFile] = useState('');
  const [petName, setPetName] = useState('');

  // =========================기피 재료 등록=====================//
  
  // ==========================사진 등록===================//
  const handleImagePreview = (e) => {
    const selectedImage = e.target.files[0];
    console.log(e)
    setFile(selectedImage)
    if (selectedImage) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setImage(reader.result);
      };
      console.log(image)
      reader.readAsDataURL(selectedImage);
    }
  };

  //==================== 제출=====================//
  const handlePetRegister = () =>{
    const formData = new FormData();
    formData.append('petImage', file)
    let dto = [{
      name: petName,
      forbiddenIngredients:[0]
    }]
    formData.append("dto", new Blob([JSON.stringify(dto)]), {type:"application/json"})

    axios.post('http://localhost:8083/api/pet', formData)
      .then((res)=>{
        console.log('axios success :', res.data)
      })
      .catch((err)=>{
        console.log('error : ', err)
      })
    }

  return(
    <div className='petContainer'>
        {/* 제목 */}
        <div className="petFormTitle">내 반려견</div>

        {/* 사진 등록 */}
        <div className='petImgContainer'>
          <div >
            {image !== defaultImageUrl && <img className='petImgBox' src={image} alt="미리보기" />}
            {image === defaultImageUrl && <img className='petImgBox' src={defaultImageUrl} alt="미리보기"  />}
          </div>
          <input className='petImgInput' type="file" onChange={handleImagePreview}/>

        </div>

        {/* pet 이름 */}
        <div className='petInfoGroup'>
          <label>이름</label>
          <input className='petInput' type="text" placeholder={''} onChange={e=>{setPetName(e.target.value)}} />
          </div>

        {/* 기피 재료 등록 */}
        <PetIngredientTagBar/>
        

      {/* 등록 버튼 */}
      <button onClick={handlePetRegister} className='petRegisterBtn'>등록하기</button>

      </div>
  )
}



export default PetRegisterForm;