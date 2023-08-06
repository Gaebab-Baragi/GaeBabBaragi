// import Comment from "../components/form/LoginForm";
import axios from "axios";
import { useEffect, useState } from "react";
import { useSelector } from "react-redux";

function RecipeDetailPage({id}) {

  const [recipeDetail, setRecipeDetail] = useState({})

  // 재료 detail 가져오기
  useEffect(()=>{
  axios.get(`api/recipes/1`)
  .then((res)=>{
    console.log('recipe list: ', res.data)
    setRecipeDetail(res.data)
  })
  .catch((err)=>{
    console.log(err)
  })
  },[])

  return (
    <div className="RecipeDetailContainer">
      {/* 레시피 제목 */}
      <h1>{recipeDetail.title}</h1>
      {/* 레시피 사진 */}
      <div >
        <h2>이미지임 s3 어케함?</h2>
        {/* <img src={recipeDetail.imgUrl} alt="" /> */}
      </div>
      {/* 레시피 조회수 & 좋아요 수 */}
      <div>
        <p>좋아요 수가 response에 없어요ㅠㅠ</p>
      </div>
      {/* user profile */}
      <div>

      </div>
      {/* 레시피 설명 */}
      <div>
        <p>{recipeDetail.description}</p>
      </div>
      {/* 재료 */}

      {/* 단계 설명*/}

      {/* 댓글 */}
    </div>
  );
  }
export default RecipeDetailPage;