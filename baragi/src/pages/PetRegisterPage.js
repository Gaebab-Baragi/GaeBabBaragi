import './PetRegisterPage.css'
import { ReactTags } from "react-tag-autocomplete";
import React, {useCallback, useEffect, useState} from "react";
import axios from 'axios';

function PetRegisterPage() {
  const [selected, setSelected] = useState([]);
  const defaultImageUrl = './기본이미지.PNG';
  const [image, setImage] = useState(defaultImageUrl);
  const [file, setFile] = useState('');
  const [petName, setPetName] = useState('');

  // =========================기피 재료 등록=====================//
  const suggestions = [
    {value:1, label:'고구마'},
    {value:2, label:'감자'},
    {value:3, label:'소고기'},
    {value:4, label:'돼지고기'}
  ]

  const onAdd = useCallback(
    (newTag) => {
      setSelected([...selected, newTag])
    },
    [selected]
  )

  const onDelete = useCallback(
    (tagIndex) => {
      setSelected(selected.filter((_, i) => i !== tagIndex))
    },
    [selected]
  )
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
    formData.append('file', file)
    let variables = [{
      petName: petName,
      
    }]
    formData.append("data", new Blob([JSON.stringify(variables)]), {type:"application/json"})

    axios.post('/api/pets', formData )
      .then((res)=>{
        console.log('axios success :', res.data)
      })
      .catch((err)=>{
        console.log('error : ', err)
      })
    }

  return(
    <div className="pageContainer">

      <div className='petContainer'>
        {/* 제목 */}
        <div className="petFormTitle">반려견 등록하기</div>

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
          <input className='petInput' type="text" placeholder="" onChange={e=>{setPetName(e.target.value)}} />
          </div>

        {/* 못 먹는 재료 */}
        <div className='petInfoGroup'>
          <label >못 먹는 재료 선택</label>
          <ReactTags
          suggestions={suggestions}
          placeholderText="재료 선택"
          selected={selected}
          onAdd={onAdd}
          onDelete={onDelete}
          noOptionsText="일치하는 재료가 없습니다."
          allowBackspace={true}
          />
        </div>

      {/* 등록 버튼 */}
      <button onSubmit={handlePetRegister} className='petRegisterBtn'>등록하기</button>

      </div>
    </div>
  )
}

export default PetRegisterPage;