import React, { useState, useEffect } from "react";
import styled from "styled-components";
import Paging from "../Paging";
import RecipeCard from "../RecipeCard";
import axios from "axios";

const CardContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  
  max-width : 1200px;
`;

const StyledCardWrapper = styled.div`
  margin-bottom: 20px;
`;

function MyBookmarks({rowNum, list}) {
  const [items, setItems] = useState([]); // 리스트에 나타낼 아이템
  const [count, setCount] = useState(0); // 아이템 총 개수
  const [currentpage, setCurrentpage] = useState(1); // 현재페이지
  const [cardsPerRow, setCardsPerRow] = useState(4); // Number of cards in each row
  const rowsPerPage = rowNum; // Number of rows per page
  const [postPerPage, setPostPerPage] = useState(cardsPerRow * rowsPerPage);

  // 레시피 목록 가져오기!!
  useEffect(() => {
    axios.get(process.env.REACT_APP_BASE_URL +"/api/bookmark/my")
      .then((res) => {
          if (res.status === 200){
            setItems(res.data)
          }
      }) 
      .catch((res) => {
        console.log(res) 
      }) 
  }, []);

  useEffect(() => {
    setCount(items.length);
  }, [items]);
  
  useEffect(() => {
    const adjustCardsPerPage = () => {
      const screenWidth = window.innerWidth;
      if (screenWidth >= 1200) {
        return 4;
      } else if (screenWidth >= 920) {
        return 3;
      } else if (screenWidth >= 700) {
        return 2;
      } else {
        return 1;
      }
    };

    const updateCardsPerPage = () => {
      const newCardsPerRow = adjustCardsPerPage();
      setCardsPerRow(newCardsPerRow);
    };

    function handleResize() {
      setPostPerPage(cardsPerRow * rowsPerPage);
      }

    window.addEventListener("resize", updateCardsPerPage);
    return () => {
      window.removeEventListener("resize", updateCardsPerPage);
    };
  }, [window]);

  const indexOfLastPost = currentpage * postPerPage;
  const indexOfFirstPost = indexOfLastPost - postPerPage;
  
  const currentPosts = items.slice(
    indexOfFirstPost,
    Math.min(indexOfLastPost, count)
  );
  
  const setPage = (e) => {
    setCurrentpage(e);
  };

  return (
    <div>
      <CardContainer>
        {currentPosts.map((item) => (
          <StyledCardWrapper key={item.id}>
            <RecipeCard item={item} />
          </StyledCardWrapper>
        ))}
      </CardContainer>
      <Paging page={currentpage} count={count} setPage={setPage} />
    </div>
  );
}

export default MyBookmarks;