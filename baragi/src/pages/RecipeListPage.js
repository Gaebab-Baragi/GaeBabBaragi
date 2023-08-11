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
import { useDispatch } from "react-redux";
import axios from "axios";
import useDidMountEffect from "../useDidMountEffect";

function RecipeListPage() {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  // 언제가져오는 거지? 자동 렌더링이 되나??
  const dogs = useSelector((state)=>state.recipeSearch.dogs)
  const ingredients = useSelector((state)=>state.recipeSearch.ingredients)
  const title = useSelector((state)=>state.recipeSearch.keyword)
  const [filtered, setFiltered] = useState(false);
  const [filteredList, setFilteredList] = useState([])
  
  useEffect(()=>{
    
  },[])
  
  
  
  useEffect(()=>{
    console.log('filtered',filtered)
  },[])
  useDidMountEffect(()=>{
    console.log('리덕스 변화 페이지에서 감지')
    let tempIngredient = ingredients;
    if (ingredients.length === 0) {
      tempIngredient = null;
    } 
    let tempDog = dogs;
    if (dogs.length === 0) {
      tempDog = null;
    } 
    const data = {
      title : '',
      ingredients: tempIngredient,
      pets: tempDog
    }
    console.log('보내는 데이터임',data)
    // axios 요청 보내서 레시피 저장하기
    axios.post(process.env.REACT_APP_BASE_URL +'/api/recipes/searchlike', data)
      .then((res)=>{
        console.log('필터링 된 목록 가져오기 성공 : ' , res.data)
        setFilteredList(res.data)
      })
      .catch((err)=>{
        console.log('필터링 데이터 가져오기 실패')
      })

    setFiltered(true)
    },[dogs,ingredients])

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
        <h2>총 {filteredList.length} 건의 레시피가 있습니다.</h2>
        <CardPaginationList rowNum={3} filteredList={filteredList}/>
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
