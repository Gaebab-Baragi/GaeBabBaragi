import React, { useState, useRef } from 'react';

function InputImage() {
  const defaultImageUrl = './기본이미지.PNG';
  const [image, setImage] = useState(defaultImageUrl);
  const fileInputRef = useRef(null);

  // 파일 선택 시 이미지 미리보기 함수
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
  const handleButtonClick = () => {
    // Trigger the file input element click when the button is clicked
    fileInputRef.current.click();
  };
  // 이미지 업로드 함수
  // const handleImageUpload = () => {
  //   // 이미지 업로드 로직을 구현합니다.
  //   // 이미지 데이터는 `image` 상태에 있는 base64 데이터로 사용할 수 있습니다.
  //   console.log('이미지 업로드:', image);
  // };
  return (
    <>
      {/* 이미지 미리보기 */}
      <div>
        {image !== defaultImageUrl && <img src={image} alt="미리보기" style={{ width: '50%', height: '50%', marginBottom :'1%'}} />}
        {image === defaultImageUrl && <img src={defaultImageUrl} alt="미리보기" style={{ width: '50%', height: '40%' ,marginBottom :'1%' }} />}
      {/* {image!== defaultImageUrl && <img src={image} alt="미리보기" style={{ width: '200px', height: '200px' }} />} */}
      </div>
      <div >
        <button onClick={handleButtonClick}>이미지선택</button>
        <input style = {{display:'none' }} type="file" ref={fileInputRef} accept="" onChange={handleImagePreview}/>
      </div>
    </>
  );
}

export default InputImage;
