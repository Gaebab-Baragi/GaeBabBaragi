import React from 'react';
import InputImage from '../components/ui/InputImage';
import InputInfor from '../components/ui/InputInfor';
import MaterialRegist from '../components/ui/MaterialRegist';
import CookStep from '../components/ui/CookStep';
import { useDispatch } from 'react-redux';
import { requestFilteredRecipeList,updateImage} from '../redux/recipeRegisterSlice';


function RecipeRegisterPage() {
  const dispatch = useDispatch();
  const handleImageUpload = (imageData) => {
    dispatch(updateImage(imageData));
  };

  return (
    <>
    <div style={{ marginLeft:'10%' , marginRight : '10%' , marginTop : '0.5%' , marginBottom : '10%'}}>
        <h1 style={{ textAlign : 'left' }}>레시피 등록</h1>
        <h4 style={{textAlign:'left', marginLeft:'2%'}}>1. 대표사진 등록</h4>
        <div style={{ marginTop : '1%', marginBottom : '1%' , backgroundColor : '#0001', justifyContent:'center', alignItems:'center'}}>
          <InputImage handleImageUpload={handleImageUpload}></InputImage>
        </div>
        <h4 style={{ textAlign: 'left', marginLeft: '2%' }}>2. 기본 정보 입력</h4>
        <InputInfor></InputInfor>
        <h4 style={{textAlign:'left', marginLeft:'2%'}}>3. 재료 등록</h4>
        <MaterialRegist></MaterialRegist>
        <h4 style={{ textAlign: 'left', marginLeft: '2%' }}>4. 요리 순서</h4>
        <CookStep></CookStep>
        <button onClick={()=>{dispatch(requestFilteredRecipeList())}}>제출</button>
    </div>
    </>
  );
}

export default RecipeRegisterPage;






