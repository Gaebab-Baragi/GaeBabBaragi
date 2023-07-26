import React from "react";
import CardPaginationList from "../components/list/CardPagination";
import SearchBar from "../components/ui/SearchBar";
import BookData from './Data.json'
import { Routes, Route, Link, useNavigate, Outlet } from 'react-router-dom'



function RecipeListPage() {
  let navigate = useNavigate();
  const RecipeRegister = ()=> {

  }
  
  return (
    <>
    <div style ={{display:'flex' }}>
      <SearchBar data={BookData }/>
      <button onClick={()=>{navigate('/recipe-regist')}}>레시피 작성</button>
      </div>
      <div>
      // props로 필터링 된 레시피 목록 넘겨주기
      <CardPaginationList rowNum={3} />
    </div>
    </>
  )
}

export default RecipeListPage;
