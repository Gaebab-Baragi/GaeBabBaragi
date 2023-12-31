import React, { useState, useEffect } from 'react';
import Carousel from 'react-bootstrap/Carousel';
import CardComponent from '../ui/Card';
import { Stack } from 'react-bootstrap';
import './CardCarousel.css'

function CardCarousel({popularRecipes}) {
  // 반응형 슬라이드당 카드 개수를 상태로 관리
  const [cardsPerSlide, setCardsPerSlide] = useState();

  // 화면 크기가 변경될 때마다 카드 개수 업데이트
  useEffect(() => {
    const adjustCardsPerSlide = () => {
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
    
    const updateCardsPerSlide = () => {
      const newCardsPerSlide = adjustCardsPerSlide();
      setCardsPerSlide(newCardsPerSlide);
    };

    updateCardsPerSlide();
    // 화면 크기 변화 이벤트 리스너 등록
    window.addEventListener('resize', updateCardsPerSlide);

    // 컴포넌트가 언마운트될 때 리스너 제거
    return () => {
      window.removeEventListener('resize', updateCardsPerSlide);
    };
  });

  // 슬라이드당 카드 배열을 생성하는 함수
  const createSlideWithCards = (startIndex) => {
    const slideCards = [];
    for (let i = 0; i < cardsPerSlide; i++) {
      const count = startIndex + i;
      let recipe = popularRecipes[count]
      // console.log('recipe slice for carousel', recipe)
      slideCards.push(<CardComponent recipe={recipe}/>);
    }
    return slideCards;
  };

  // Carousel.Item에 들어가는 배열 생성
  const carouselItems = [];
  for (let i = 0; i < 12; i += cardsPerSlide) {
    const slideCards = createSlideWithCards(i);
    carouselItems.push(
      <Carousel.Item key={i}>
        <Stack
          direction='horizontal'
          className='h-100 justify-content-center align-items-center'
          gap={5}
        >
          {slideCards}
        </Stack>
      </Carousel.Item>
    );
  }

  return <Carousel>{carouselItems}</Carousel>;
}

export default CardCarousel;