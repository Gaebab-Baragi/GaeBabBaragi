import React, { useCallback, useEffect, useState, useRef } from "react";
import axios from "axios";
import './css/MemberModification.css'
import './css/BasicForm.css'
import './css/PetRegisterForm.css'
import PetIngredientTagBar from "../ui/PetIngredientTagBar";
import defaultImg from "./default.png"
import Toast from "../ui/Toast";
import Swal from "sweetalert2";

function PetRegisterForm({petInfo, idx, rerender}) {
  const [petName, setPetName] = useState('')
  const [forbiddens, setForbiddens] = useState([])
  const [petImage, setPetImage] = useState(defaultImg)
  const [file, setFile] = useState('')
  const [selected, setSelected] = useState([]);

  useEffect(()=>{
    if (petInfo) {
      setPetName(petInfo.name);
      setPetImage(petInfo.imgUrl);
      setFile(null);
      setForbiddens(petInfo.forbiddens);
    }
  },[petInfo])

  useEffect(() => {
    if (!petImage) {
      setPetImage(defaultImg);
      setFile(null);
    }
  }, [petImage, file]);

  const selectIngredients = (sel) => {
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

  const registerPet = async (e) => {
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


    await axios
      .post(process.env.REACT_APP_BASE_URL +"/api/pet", formData, {
        headers : { 'Content-Type' : 'multipart/form-data'}
      })
      .then((res) => {
        Toast.fire("추가되었습니다.", "", "success");
        rerender();
      })
      .catch((err) => {
        Toast.fire("오류 발생", "", "error");
      });
  }

  const modifyPet = async (e) => {
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

    await axios
      .post(process.env.REACT_APP_BASE_URL +"/api/pet/modify", formData, {
        headers : { 'Content-Type' : 'multipart/form-data'}
      })
      .then((res) => {
        Toast.fire("수정되었습니다.", "", "success");
      })
      .catch((err) => {
        Toast.fire("오류 발생", "", "error");
      });
  }


  const removePet = () => {
    Swal.fire({
      title: '해당 반려견을\n목록에서 제외시킵니다',
      showDenyButton: true,
      confirmButtonText: '예',
      confirmButtonColor: 'red',
      denyButtonText: `취소`,
      denyButtonColor: 'gray'
    }).then((result) => {
      /* Read more about isConfirmed, isDenied below */
      if (result.isConfirmed) {
        axios.delete(process.env.REACT_APP_BASE_URL + "/api/pet/" + petInfo.id)
          .then((res) => {
            Toast.fire("목록에서 제외시켰습니다", "", "success");
            rerender();
          })
          .catch((res) => {
            Toast.fire("삭제할 수 없습니다.", "", "error");
          })
      } else if (result.isDenied) {

      }
    })
  }

  return (
    <div className="formContainer">
      {/* 제목 */}
      <form className = "form">
      <div className="formTitle">내 반려견</div>
      
      {
        petInfo? 
        <div className="petRemoveBtn" onClick={() => removePet()}> X </div>
        : <></>
      }
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