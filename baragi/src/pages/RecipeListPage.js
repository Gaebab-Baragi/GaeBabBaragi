import React from "react";
import CardPaginationList from "../components/list/CardPagination";
import SearchBar from "../components/ui/SearchBar";
import IngredientTagBar from "../components/ui/IngredientTagBar";
import BookData from './Data.json'
import { Routes, Route, Link, useNavigate, Outlet } from 'react-router-dom'



function RecipeListPage() {
  let navigate = useNavigate();
  
  return (
    <div>
      <SearchBar data={BookData}/>
      <IngredientTagBar/>
      <h2>총 100 건의 레시피가 있습니다.</h2>
      <button onClick={()=>navigate('/recipe-regist')}>레시피 작성</button>
      <CardPaginationList rowNum={3} />
    </div>
  )
}

export default RecipeListPage;
