import React, { useState, useEffect } from "react";
import styled from "styled-components";
import { Paging } from "../components/ui/Pagination";
import CardComponent from "../components/ui/Card";

const CardContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  margin-left: 10%;
  margin-right: 10%;
`;
const StyledCardWrapper = styled.div`
  margin-bottom: 20px;
`;

function Posts() {
  const [items, setItems] = useState([]); // 리스트에 나타낼 아이템
  const [count, setCount] = useState(0); // 아이템 총 개수
  const [currentpage, setCurrentpage] = useState(1); // 현재페이지
  const cardsPerRow = 4; // Number of cards in each row
  const rowsPerPage = 3; // Number of rows per page
  const [postPerPage, setPostPerPage] = useState(cardsPerRow * rowsPerPage);

  useEffect(() => {
    fetch("https://jsonplaceholder.typicode.com/posts")
      .then((res) => res.json())
      .then((data) => setItems(data));
  }, []);

  useEffect(() => {
    setCount(items.length);
  }, [items]);

  useEffect(() => {
    function handleResize() {
      setPostPerPage(cardsPerRow * rowsPerPage);
    }

    window.addEventListener("resize", handleResize);
    return () => {
      window.removeEventListener("resize", handleResize);
    };
  }, []);

  const indexOfLastPost = currentpage * postPerPage;
  const indexOfFirstPost = indexOfLastPost - postPerPage;
  const currentPosts = items.slice(indexOfFirstPost, indexOfLastPost);

  const setPage = (e) => {
    setCurrentpage(e);
  };

  return (
    <div>
      <CardContainer>
        {currentPosts.map((item) => (
          <StyledCardWrapper key={item.id}>
            <CardComponent title={item.title} content={item.body} />
          </StyledCardWrapper>
        ))}
      </CardContainer>
      <Paging page={currentpage} count={count} setPage={setPage} />
    </div>
  );
}

export default Posts;
