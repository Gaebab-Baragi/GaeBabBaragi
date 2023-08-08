import React, { useCallback, useEffect, useState, useRef } from "react";
import axios from "axios";
import './css/MemberModification.css'
import './css/BasicForm.css'
import PetIngredientTagBar from "../ui/PetIngredientTagBar";
import { useDispatch } from "react-redux";
import defaultImg from "./default.png"
import { useNavigate } from "react-router-dom";

function PetRegisterForm({petInfo, idx}) {
  const [pet, setPet] = useState({})
  const [petName, setPetName] = useState('')
  const [forbiddens, setForbiddens] = useState([])
  const [petImage, setPetImage] = useState(defaultImg)
  const [file, setFile] = useState('')
  const [selected, setSelected] = useState([]);

  const navigate = useNavigate();

  useEffect(()=>{
    if (petInfo) {
      setPet(petInfo.pet);
      setPetName(petInfo.name);
      setPetImage(petInfo.imgUrl);
      setForbiddens(petInfo.forbiddens);
    }
  },[idx])

  useEffect(() => {
    if (!petImage) {
      setPetImage(defaultImg);
      setFile(null);
    }
  }, [petImage, file]);

  const selectIngredients = (sel) => {
    console.log("sel:", sel);
    setSelected(sel);
  }

  // ==========================사진 등록===================//
  const handleImagePreview = (e) => {
    e.preventDefault();
    console.log(idx);
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

  const registerPet = (e) => {
    e.preventDefault();
    let tmp = []
    selected.map((i)=>{
      tmp.push(i.value)
    })

    const formData = new FormData();
    console.log(file);
    formData.append("petImage", file);
    const datas = {
      name: petName,
      forbiddenIngredients: tmp 
    };
    formData.append(
      "dto",
      new Blob([JSON.stringify(datas)], { type: "application/json" })
    );


    axios
      .post("/api/pet", formData, {
        headers : { 'Content-Type' : 'multipart/form-data'}
      })
      .then((res) => {
        console.log("axios success :", res.data);
        alert("추가되었습니다.")
        navigate("/my-pet-list/"+ idx);
      })
      .catch((err) => {
        console.log("error : ", err);
      });
  }

  const modifyPet = (e) => {
      e.preventDefault();
      let tmp = []
      selected.map((i)=>{
        tmp.push(i.value)
      })
      const formData = new FormData();
      if (file) formData.append("petImage", file);
      const datas = {
        id : petInfo.id,
        name: petName,
        forbiddenIngredients: tmp 
      };
      formData.append(
        "dto",
        new Blob([JSON.stringify(datas)], { type: "application/json" })
      );

    axios
      .post("/api/pet/modify", formData, {
        headers : { 'Content-Type' : 'multipart/form-data'}
      })
      .then((res) => {
        console.log("axios success :", res.data);
        alert("수정되었습니다.")
      })
      .catch((err) => {
        console.log("error : ", err);
      });


  }



  return (
    <div className="formContainer">
      {/* 제목 */}
      <form className = "form">
      <div className="formTitle">내 반려견</div>

      {/* 사진 등록 */}
      <label htmlFor={"upload-"+idx} className="custom-file-upload fas">
          <div className="img-wrap img-upload" >
              <img className="member-profile-img" src={petImage} htmlFor={"upload-"+idx} alt="프로필"/>
          </div>
          <input id={"upload-"+idx} className="photo-upload" type="file" onChange={(e) => {handleImagePreview(e)}}/> 
      </label>

      {/* pet 이름 */}
      <div className="formGroup">
        <input
          className="formInput"
          type="text"
          placeholder={petInfo? petInfo.name : "이름이 뭔가요?"}
          onChange={(e) => {
            setPetName(e.target.value);
          }}
        />
      </div>

      {/* 기피 재료 등록 */}
      {petInfo? (
        <PetIngredientTagBar forbiddens={petInfo.forbiddens} selectIngredients={selectIngredients} />
      ) : <PetIngredientTagBar selectIngredients={selectIngredients} id={0}/>}

      {/* 등록 버튼 */}
      <button className="submitButton" onClick={(e)=>{
          e.preventDefault();
          if (petInfo) modifyPet(e);
          else registerPet(e);
        }}>
          {
            petInfo? '수정하기' : '등록하기'
        }
      </button>
      </form>
    </div>
  );
}

export default PetRegisterForm;