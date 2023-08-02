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
        <InputImage></InputImage>
        <InputInfor></InputInfor>
        <MaterialRegist></MaterialRegist>
        <CookStep></CookStep>
        <button onClick={()=>{dispatch(requestFilteredRecipeList())}}>제출</button>
    </div>
    </>
  );
}

export default RecipeRegisterPage;
