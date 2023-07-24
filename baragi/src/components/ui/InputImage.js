import React, { useState } from 'react';

function InputImage() {
  const defaultImageUrl = './기본이미지.PNG';
  const [image, setImage] = useState(defaultImageUrl);
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

  // 이미지 업로드 함수
  const handleImageUpload = () => {
    // 이미지 업로드 로직을 구현합니다.
    // 이미지 데이터는 `image` 상태에 있는 base64 데이터로 사용할 수 있습니다.
    console.log('이미지 업로드:', image);
  };

  return (
    <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', padding: '100px 0'}}>
        <div>1. 대표사진 등록</div>
        <div>
      {/* 이미지 미리보기 */}
      {image!== defaultImageUrl && <img src={image} alt="미리보기" style={{ width: '200px', height: '200px' }} />}
        <br />
      {/* 파일 업로드 입력 필드 */}
      <input type="file" accept="image/*" onChange={handleImagePreview} />

      {/* 이미지 업로드 버튼 */}

      <button onClick={handleImageUpload}>이미지 업로드</button>
    </div>
    </div>
  );
}

export default InputImage;