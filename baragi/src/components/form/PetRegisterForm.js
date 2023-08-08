import React, { useCallback, useEffect, useState, useRef } from "react";
import axios from "axios";
import './css/MemberModification.css'
import './css/BasicForm.css'
import PetIngredientTagBar from "../ui/PetIngredientTagBar";
import { useDispatch } from "react-redux";

function PetRegisterForm({petInfo}) {
  const dispatch = useDispatch();
  const [pet, setPet] = useState({})
  const [petName, setPetName] = useState('')
  const defaultImageUrl = "./default.png";
  const [petImage, setPetImage] = useState(defaultImageUrl)
  const [file, setFile] = useState('')
  const [selected, setSelected] = useState([]);

  useEffect(()=>{
    if (petInfo) {
      setPet(petInfo.pet);
      setPetName(petInfo.name);
      setPetImage(petInfo.imgUrl);
    }
  },[])

  const selectIngredients = (sel) => {
    setSelected(sel);
  }

  // ==========================사진 등록===================//
  const handleImagePreview = (e) => {
    e.preventDefault();
    const selectedImage = e.target.files[0];
    setPetImage(selectedImage)
    setFile(selectedImage)
    if (selectedImage) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setPetImage(reader.result);
      };
      reader.readAsDataURL(selectedImage);
    }
  };

  return (
    <div className="formContainer">
      {/* 제목 */}
      <form className = "form">
      <div className="formTitle">내 반려견</div>

      {/* 사진 등록 */}
      <label htmlFor="photo-upload" className="custom-file-upload fas">
          <div className="img-wrap img-upload" >
              <img className="member-profile-img" src={petImage} htmlFor="photo-upload" alt="프로필"/>
          </div>
          <input id="photo-upload" type="file" onChange={(e) => {handleImagePreview(e)}}/> 
      </label>

      {/* pet 이름 */}
      <div className="formGroup">
        <input
          className="formInput"
          type="text"
          placeholder={petInfo? pet.name : "이름이 뭔가요?"}
          onChange={(e) => {
            setPetName(e.target.value);
          }}
        />
      </div>

      {/* 기피 재료 등록 */}
      {petInfo? (
        <PetIngredientTagBar forbiddens={pet.forbiddens} selectIngredients={selectIngredients} />
      ) : <PetIngredientTagBar selectIngredients={selectIngredients}/>}

      {/* 등록 버튼 */}
      <button className="petRegisterBtn" onClick={()=>{
        
        }}>
        등록하기
      </button>
      </form>
    </div>
  );
}

export default PetRegisterForm;