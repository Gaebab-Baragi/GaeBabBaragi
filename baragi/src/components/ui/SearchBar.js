import React, { useEffect, useState } from "react";
import "./SearchBar.css";
import SearchIcon from '@mui/icons-material/Search';
import CloseIcon from '@mui/icons-material/Close';
import '@mui/icons-material'

function SearchBar({  data }) {
  const [filteredData, setFilteredData] = useState([]);
  const [wordEntered, setWordEntered] = useState("");
  const [needSearch, setNeedSearch] = useState(true);

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
          {filteredData.length === 0 ? (
            <SearchIcon />
          ) : (
            <CloseIcon id="clearBtn" onClick={clearInput} />
          )}
        </div>
      </div>
      {filteredData.length != 0 && needSearch && (
        // <div className="dataResultWrapper">
          <div className="dataResult">
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