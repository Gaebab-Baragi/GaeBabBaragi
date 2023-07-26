// 수정 필요한 사항
// 검색 icon 눌렀을 경우, axios 요청 보내는 함수
// wordEntered Redux 와 연결하기!

import React, { useEffect, useState, useRef } from "react";
import "./SearchBar.css";
import SearchIcon from '@mui/icons-material/Search';
import '@mui/icons-material'
import { useDispatch } from "react-redux";
import {updateKeyword} from "../../redux/searchRecipeSlice";

function SearchBar({  data }) {
  const [filteredData, setFilteredData] = useState([]);
  const [wordEntered, setWordEntered] = useState("");
  const [needSearch, setNeedSearch] = useState(true);
  const dataResultRef = useRef(null);
  const dispatch = useDispatch();

  useEffect(()=>{
    dispatch(updateKeyword(wordEntered))
  }, [wordEntered])

   // Add event listeners to the document when the component mounts
  useEffect(() => {
    document.addEventListener("click", handleClickOutside);
    document.addEventListener("keydown", handleKeyDown);

    return () => {
      // Remove the event listeners when the component unmounts
      document.removeEventListener("click", handleClickOutside);
      document.removeEventListener("keydown", handleKeyDown);
    };
  }, []);
  // Handle clicks outside the dataResult div
  const handleClickOutside = (event) => {
    if (dataResultRef.current && !dataResultRef.current.contains(event.target)) {
      // Click occurred outside the dataResult div, so close it
      setFilteredData([]);
    }
  };
  // Handle keydown (e.g., Esc key)
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
        <div className="searchIcon">
          <SearchIcon/>
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

        // </div>
      )}
    </div>
  );
}

export default SearchBar;

