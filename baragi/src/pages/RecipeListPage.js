import React, { useState } from "react";
import CardPaginationList from "../components/list/CardPagination";
import SearchBar from "../components/ui/SearchBar";
import IngredientTagBar from "../components/ui/IngredientTagBar";
import BookData from './Data.json'
import { useNavigate } from 'react-router-dom'
import CardCarousel from "../components/list/CardCarousel";
import './RecipeListPage.css'
import DogSelectBar from "../components/ui/DogSelectBar";

function RecipeListPage() {
  let navigate = useNavigate();
  const [filtered, setFiltered] = useState(false);

  return (
    <div>
      {/* 검색창 */}
      <div className="searchContainer">
        <SearchBar data={BookData}/>
        <IngredientTagBar/> 
        <DogSelectBar/>
      </div>
    {
      filtered
      // 검색 --> 레시피 목록 보이게
      ? 
      <div>
        <h2>총 100 건의 레시피가 있습니다.</h2>
        <button onClick={()=>navigate('/recipe-regist')}>레시피 작성</button>
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
