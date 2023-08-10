import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import CardPaginationList from "../components/list/CardPagination";
import SearchBar from "../components/ui/SearchBar";
import IngredientTagBar from "../components/ui/IngredientTagBar";
import BookData from './Data.json'
import { useNavigate } from 'react-router-dom'
import CardCarousel from "../components/list/CardCarousel";
import './css/RecipeListPage.css'
import DogSelectBar from "../components/ui/DogSelectBar";

function RecipeListPage() {
  const navigate = useNavigate();
  // 언제가져오는 거지? 자동 렌더링이 되나??
  const requestHappen = useSelector((state) => state.recipeSearch.requestHappen);
  const [filtered, setFiltered] = useState(false);

  useEffect(()=>{
    if (requestHappen) {
      setFiltered(true);
    }
  },[requestHappen])

  return (
    <div>
      {/* 검색창 */}
      <div className="searchContainer">
        <SearchBar data={BookData}/>
        <IngredientTagBar/> 
        <DogSelectBar/>
        <button onClick={()=>navigate('/recipe-register')}>레시피 작성</button>
      </div>
    {
    filtered
      // 검색 --> 레시피 목록 보이게
      ? 
      <div>
        <h2>총 100 건의 레시피가 있습니다.</h2>
        <CardPaginationList rowNum={3} />
      </div>
      // 첫 화면 --> 인기 레시피 캐로셀로 보여주기
      :
      <div className="tempContainer">
        <h1 className="tempContainerTitle">인기 레시피</h1>
        <CardCarousel/>
      </div>
    }
    </div>
  )
}

export default RecipeListPage;


// redux에서 검색 여부 받아와서 화면 바꿔주기
