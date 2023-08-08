import { ReactTags } from "react-tag-autocomplete";
import React, { useCallback, useEffect, useState, useRef } from "react";
import axios from "axios";
import "./PetRegisterForm.css";
import PetIngredientTagBar from "../ui/PetIngredientTagBar";
import { compose } from "@reduxjs/toolkit";
import { useNavigate } from "react-router";

function PetListForm({ pet }) {
  const navigate = useNavigate();
  const [data, setData] = useState([]);
  const [forbidden, setForbidden] = useState();
  const defaultImageUrl = "./기본이미지.PNG";

  useEffect(()=>{
    console.log('pet is ', pet)
    setData(pet)
    const tmp = []
    pet.forbiddens.map((i)=>{
      tmp.push(i.ingredientName)
    })
    setForbidden(tmp)
    console.log('tmp : ', tmp)
  },[])


  return (
    <div className="petContainer">
      {/* 제목 */}
      <div className="petFormTitle">내 반려견</div>

      {/* 사진  */}
      <div className="petImgContainer">
        <div>
            <img className="petImgBox" src={data.imgUrl} alt="미리보기" />
        </div>
      </div>

      {/* pet 이름 */}
      <div className="petInfoGroup">
        <label>이름</label>
        <input
          className="petInput"
          type="text"
          placeholder={data.name}
          disabled={true}
        />
      </div>

      {/* 기피 재료 등록 */}
      <div className="petInfoGroup">
        <label>안 먹는 재료</label>
        <input disabled={true} className="petInput" type="text" placeholder={forbidden}/>
      </div>

      {/* 등록 버튼 */}
      <button onClick={handleNavigate} className="petRegisterBtn">
        수정하기
      </button>
    </div>
  );
}

export default PetListForm;
