import React from "react";
import CardPaginationList from "../components/list/CardPagination";
import SearchBar from "../components/ui/SearchBar";
import BookData from './Data.json'

function RecipeListPage() {
  return (
    <div>
      <SearchBar data={BookData }/>
      // props로 필터링 된 레시피 목록 넘겨주기
      <CardPaginationList rowNum={3} />
    </div>
  )
}

export default RecipeListPage;
