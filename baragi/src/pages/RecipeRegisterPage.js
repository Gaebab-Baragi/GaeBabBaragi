import React from 'react';
import InputImage from '../components/ui/InputImage';
import InputInfor from '../components/ui/InputInfor';
import MaterialRegist from '../components/ui/MaterialRegist';
import CookStep from '../components/ui/CookStep';
import { useDispatch } from 'react-redux';
import { requestFilteredRecipeList} from '../redux/recipeRegisterSlice';


function RecipeRegisterPage() {
  const dispatch = useDispatch();
  

  return (
    <>
    <div style={{ marginLeft:'10%' , marginRight : '10%' , marginTop : '0.5%' , marginBottom : '10%'}}>
        <h1 style={{ textAlign : 'left' }}>레시피 등록</h1>
        <h4 style={{textAlign:'left', marginLeft:'2%', background : '#0002' }}>1. 대표사진 등록</h4>
        <InputImage></InputImage>
        <h4 style={{ textAlign: 'left', marginLeft: '2%', background : '#0002' }}>2. 기본 정보 입력</h4>
        <InputInfor></InputInfor>
        <h4 style={{textAlign:'left', marginLeft:'2%', background : '#0002'}}>3. 재료 등록</h4>
        <MaterialRegist></MaterialRegist>
        <h4 style={{ textAlign: 'left', marginLeft: '2%', background : '#0002' }}>4. 요리 순서</h4>
        <CookStep></CookStep>
        <button onClick={()=>{dispatch(requestFilteredRecipeList())}}>제출</button>
    </div>
    </>
  );
}

export default RecipeRegisterPage;






