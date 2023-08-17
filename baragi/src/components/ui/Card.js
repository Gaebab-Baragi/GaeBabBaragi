import React, { useEffect, useState } from 'react';
import Card from 'react-bootstrap/Card';
import { useNavigate, useSearchParams } from 'react-router-dom';
import axios  from 'axios';
import './Card.css'

function CardComponent({ recipe }) {
  const navigate = useNavigate();
  const [likes, setLikes] = useState(0);
  const handleNavigateDetail = () => {
    navigate(`/recipe-detail/${recipe.id}`);
  };

  // 좋아요 수 가져오기
  useEffect(()=>{
    axios.get(process.env.REACT_APP_BASE_URL+`/api/bookmark/${recipe.id}`)
    .then((res)=>{
      // console.log('좋아요 수',res.data)
      setLikes(res.data)
    })
    .catch((err)=>{
      // console.log(err)
    })
  },[])

  const truncateText = (text, maxLength) => {
    if (text.length > maxLength) {
      return text.slice(0, maxLength) + '...';
    }
    return text;
  };

  return (
    <Card className='card-hover' onClick={handleNavigateDetail} style={{ width: '16.5rem', height: '400px' }}>
      <Card.Img style={{ height: '200px' }} variant="top" src={recipe.imgUrl} />
      <Card.Body style={{height:'auto'}}>
        <Card.Title>{recipe.title}</Card.Title>
        <Card.Text style={{textAlign:'left'}}>{truncateText(recipe.description, 70)}</Card.Text>
      </Card.Body>
      <Card.Text style={{ marginBottom: '5%', display: 'flex', justifyContent: 'space-around', alignItems: 'center' }}>
        <span style={{ display: 'flex', alignItems: 'center' }}>
          <ion-icon name="eye" size="small" style={{ marginRight: '5px' }}></ion-icon>
          {recipe.hit}
        </span>
        <span>|</span>
        <span style={{ display: 'flex', alignItems: 'center' }}>
          <ion-icon name="heart" size="small" style={{ marginRight: '5px' }}></ion-icon>
          {likes}
        </span>
        <span>|</span>
        <span style={{ display: 'flex', alignItems: 'center' }}>
          <ion-icon name="person" size="small" style={{ marginRight: '5px' }}></ion-icon>
          {recipe.member.nickname}
        </span>
      </Card.Text>

    </Card>
  );
}

export default CardComponent;