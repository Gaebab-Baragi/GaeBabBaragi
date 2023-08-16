import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import './css/RecipeListPage.css'
import CardPaginationList from "../components/list/CardPagination";
import SearchBar from "../components/ui/SearchBar";
import IngredientTagBar from "../components/ui/IngredientTagBar";
import CardCarousel from "../components/list/CardCarousel";
import DogSelectBar from "../components/ui/DogSelectBar";
import axios from "axios";
import { useNavigate } from 'react-router-dom'
import useDidMountEffect from "../useDidMountEffect";
import Toast from "../components/ui/Toast";
import Modal from "../components/ui/modal/Modal";
import ObjectDetectionPage from "./ObjectDetectionPage";

function RecipeListPage() {
  const navigate = useNavigate();
  const user = useSelector((state)=>state.user)
  const dogs = useSelector((state)=>state.recipeSearch.dogs)
  const ingredients = useSelector((state)=>state.recipeSearch.ingredients)
  const title = useSelector((state)=>state.recipeSearch.title)
  const [filtered, setFiltered] = useState(false);
  const [filteredList, setFilteredList] = useState([])
  const [recipeTitleList, setRecipeTitleList] = useState([])
  const [popularRecipes, setPopularRecipes] = useState([])
  const [showCarousel, setShowCarousel] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  
  // 레시피 제목 가져오기
  useEffect(()=>{
    console.log(process.env.REACT_APP_BASE_URL + '/api/recipes/recipestitle')
    axios.get(process.env.REACT_APP_BASE_URL + '/api/recipes/recipestitle')
    .then((res)=>{
      console.log('레시피 제목 가져오기', res.data)
      setRecipeTitleList(res.data)
    })
    .catch((err)=>{
      console.log('레시피 제목 못 가져옴', err)
    })
  },[])

  // 추천 레시피 가져오기
  useEffect(()=>{
    axios.get(process.env.REACT_APP_BASE_URL + '/api/recipes/popular')
    .then((res)=>{
      console.log('인기 레시피 가져옴', res.data.popularRecipes)
      setPopularRecipes(res.data.popularRecipes)
      setShowCarousel(true)

    })
    .catch((err)=>{
      console.log('인기 레시피 못 가져옴', err)
    })
  },[])
  
  // 검색에 변화 있을 떄 필터링된 레시피 가져오기
  useDidMountEffect(()=>{
    console.log('redux에서 변화가 일어남', title, ingredients, dogs)
    let tempIngredient = ingredients;
    if (ingredients.length === 0) {
      tempIngredient = null;
    } 
    let tempDog = dogs;
    if (dogs.length === 0) {
      tempDog = null;
    } 
    const data = {
      title : title,
      ingredients: tempIngredient,
      pets: tempDog
    }
    console.log('보내는 데이터임',data)
    // axios 요청 보내서 레시피 저장하기
    axios.post(process.env.REACT_APP_BASE_URL +'/api/recipes/searchlike', data)
      .then((res)=>{
        console.log('필터링 된 목록 가져오기 성공 : ' , res.data.recipes)
        setFilteredList(res.data.recipes)
      })
      .catch((err)=>{
        console.log('필터링 데이터 가져오기 실패')
      })

    setFiltered(true)
    },[dogs,ingredients,title])

    // 레시피 작성하기 버튼 클릭 시
    const handleRequestRecipeRegister = ()=> {
      if (user.id) {
        navigate('/recipe-register')
      } else {
        Toast.fire("로그인 후 작성할 수 있습니다.", "", "info");
        navigate('/login')
      }
    }

  return (
    <div >

      {/* 검색창 */}
      <div className="searchWrapContainer">
        <div className="searchContainer">
          <div className="searchRecipeRegister">
            <SearchBar data={recipeTitleList}/>
            <button className="recipeRegisterBtn" onClick={handleRequestRecipeRegister}>레시피 작성</button>
            <button className="objectDetectionBtn" onClick={()=>{setIsModalOpen(true)}}>재료 탐지</button>
          </div>
          <IngredientTagBar/> 
          {/* 로그인 된 회원 만 보이게 하기 */}
          {user.id &&
            <DogSelectBar/>
          }
        </div>
      </div>
    {
    filtered
      // 검색 --> 레시피 목록 보이게
      ? 
      <div>
        <h2 className="recipeLengthTitle">총 {filteredList.length} 건의 레시피가 있습니다.</h2>
        <CardPaginationList rowNum={3} filteredList={filteredList}/>
      </div>
      // 첫 화면 --> 인기 레시피 캐로셀로 보여주기
      : showCarousel &&
      <div className="tempContainer">
        <h1 className="tempContainerTitle">인기 레시피</h1>
        <CardCarousel popularRecipes={popularRecipes}/>
      </div>
    }
    {isModalOpen && (
        <Modal closeModal={() => setIsModalOpen(!isModalOpen)}>
          <ObjectDetectionPage/>
        </Modal>
    )}
    </div>
  )
}

export default RecipeListPage;

// redux에서 검색 여부 받아와서 화면 바꿔주기