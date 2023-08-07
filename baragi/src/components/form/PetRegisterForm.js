import { ReactTags } from "react-tag-autocomplete";
import React, { useCallback, useEffect, useState, useRef } from "react";
import axios from "axios";
import "./PetRegisterForm.css";
import PetIngredientTagBar from "../ui/PetIngredientTagBar";
import { useDispatch } from "react-redux";
import { setPetImage , setPetName, sendPetRegisterRequest} from "../../redux/petRegisterSlice";


function PetRegisterForm({ pet }) {
  const dispatch = useDispatch();
  const defaultImageUrl = "./기본이미지.PNG";
  const [image, setImage] = useState(defaultImageUrl);
  const [file, setFile] = useState("");

  // ==========================사진 등록===================//
  const handleImagePreview = (e) => {
    const selectedImage = e.target.files[0];
    dispatch(setPetImage(selectedImage))
    setFile(selectedImage);
    if (selectedImage) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setImage(reader.result);
      };
      console.log(file);
      reader.readAsDataURL(selectedImage);
    }
  };


  return (
    <div className="petContainer">
      {/* 제목 */}
      <div className="petFormTitle">내 반려견</div>

      {/* 사진 등록 */}
      <div className="petImgContainer">
        <div>
          {image !== defaultImageUrl && (
            <img className="petImgBox" src={image} alt="미리보기" />
          )}
          {image === defaultImageUrl && (
            <img className="petImgBox" src={defaultImageUrl} alt="미리보기" />
          )}
        </div>
        <input
          className="petImgInput"
          type="file"
          onChange={handleImagePreview}
        />
      </div>

      {/* pet 이름 */}
      <div className="petInfoGroup">
        <label>이름</label>
        <input
          className="petInput"
          type="text"
          placeholder={""}
          onChange={(e) => {
            dispatch(setPetName(e.target.value))
          }}
        />
      </div>

      {/* 기피 재료 등록 */}
      <PetIngredientTagBar/>

      {/* 등록 버튼 */}
      <button className="petRegisterBtn" onClick={()=>{
        dispatch(sendPetRegisterRequest());
        
        }}>
        등록하기
      </button>
    </div>
  );
}

export default PetRegisterForm;
