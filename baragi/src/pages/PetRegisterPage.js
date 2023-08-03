import './PetRegisterPage.css'
import { ReactTags } from "react-tag-autocomplete";
import React, {useCallback, useEffect, useState} from "react";

function PetRegisterPage() {
  const [selected, setSelected] = useState([]);
  const defaultImageUrl = './기본이미지.PNG';
  const [image, setImage] = useState(defaultImageUrl);

  // 기피 재료 등록
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
  // 사진 등록
  const handleImagePreview = (e) => {
    const selectedImage = e.target.files[0];
    if (selectedImage) {
      const reader = new FileReader();
      reader.onloadend = () => {
        setImage(reader.result);
      };
      reader.readAsDataURL(selectedImage);
    }
  };

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
          <input className='petInput' type="text" placeholder="" />
          </div>

        {/* 나이 */}
        <div className='petInfoGroup'>
          <label>나이</label>
          <input className='petInput' type="text" />
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

        {/* 몸무게 */}
        <div className='petInfoGroup'> 
          <label >몸무게</label>
          <input className='petInput' type="number" />
        </div>

      {/* 등록 버튼 */}
      <button className='petRegisterBtn'>등록하기</button>

      </div>
    </div>
  )
}

export default PetRegisterPage;