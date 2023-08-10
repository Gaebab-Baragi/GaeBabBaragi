// 수정 필요한 사항
// 검색 icon 눌렀을 경우, axios 요청 보내는 함수
// wordEntered Redux 와 연결하기!

import React, { useEffect, useState, useRef } from "react";
import "./SearchBar.css";
import { useDispatch } from "react-redux";
import {updateKeyword, requestFilteredRecipeList} from "../../redux/recipeSearchSlice";
import useDidMountEffect from "../../useDidMountEffect";

function SearchBar({  data }) {
  const [filteredData, setFilteredData] = useState([]);
  const [wordEntered, setWordEntered] = useState("");
  const [needSearch, setNeedSearch] = useState(true);
  const dataResultRef = useRef(null);
  const dispatch = useDispatch();

  // useDidMountEffect(()=>{
  //   dispatch(updateKeyword(wordEntered))
  // }, [wordEntered])

  useEffect(() => {
    document.addEventListener("click", handleClickOutside);
    document.addEventListener("keydown", handleKeyDown);

    return () => {
      document.removeEventListener("click", handleClickOutside);
      document.removeEventListener("keydown", handleKeyDown);
    };
  }, []);

  // esc 키 누르거나 빈 화면 누를 때 dataResult 창 없애기
  const handleClickOutside = (event) => {
    if (dataResultRef.current && !dataResultRef.current.contains(event.target)) {
      setFilteredData([]);
    }
  };
  const handleKeyDown = (event) => {
    if (event.keyCode === 27) {
      // 27 is the keyCode for the Esc key
      setFilteredData([]);
    }
  };

  useEffect(()=>{
    if (wordEntered==="") {
      setNeedSearch(true)
    }
  },[wordEntered])
  

  const handleFilter = (event) => {
    const searchWord = event.target.value;
    setWordEntered(searchWord);
    const newFilter = data.filter((value) => {
      return value.title.toLowerCase().includes(searchWord.toLowerCase());
    });

    if (searchWord === "") {
      setFilteredData([]);
    } else {
      setFilteredData(newFilter);
    }
  };

  const clearInput = () => {
    setFilteredData([]);
    setWordEntered("");
  };

  //===================== axios 요청 보내기=====================//
  const handleRequestFilteredList  = (e)=>{
    e.preventDefault();
    dispatch(updateKeyword(wordEntered))
  }


  return (

    <div className="search">
      <div className="searchInputs">
        <input
          className="searchInput"
          type="text"
          placeholder='레시피를 검색해보세요'
          value={wordEntered}
          onChange={handleFilter}
        />
        <div onClick={handleRequestFilteredList} className="searchIcon">
          {/* <SearchIcon/> */}
          <ion-icon name="search-outline"></ion-icon>
        </div>
      </div>
      {filteredData.length != 0 && needSearch && (
        // <div className="dataResultWrapper">
          <div className="dataResult" ref={dataResultRef}>
            {filteredData.slice(0, 15).map((value, key) => {
              return (
                <p className="titleClick" onClick={()=>{
                  setWordEntered(value.title);
                  setNeedSearch(false);
                }}>{value.title} </p>
              );
            })}
          </div>
      )}
    </div>
  );
}

export default SearchBar;

