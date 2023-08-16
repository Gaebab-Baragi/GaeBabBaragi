import React from 'react';
import Card from 'react-bootstrap/Card';
import { useNavigate } from 'react-router-dom';

function CardComponent({ recipe }) {
  const navigate = useNavigate();

  const handleNavigateDetail = () => {
    navigate(`/recipe-detail/${recipe.id}`);
  };

  const truncateText = (text, maxLength) => {
    if (text.length > maxLength) {
      return text.slice(0, maxLength) + '...';
    }
    return text;
  };

  return (
    <Card onClick={handleNavigateDetail} style={{ width: '16rem', height: '400px' }}>
      <Card.Img style={{ height: '200px' }} variant="top" src={recipe.imgUrl} />
      <Card.Body style={{height:'auto'}}>
        <Card.Title>{recipe.title}</Card.Title>
        <Card.Text style={{textAlign:'left'}}>{truncateText(recipe.description, 70)}</Card.Text>
      </Card.Body>
      <Card.Text style={{ marginBottom:'5%', display: 'flex', justifyContent: 'space-around' }}>
        <span>조회 수: {recipe.hit}</span>
        <span>|</span>
        <span>작성자: {recipe.member.nickname}</span>
      </Card.Text>
    </Card>
  );
}

export default CardComponent;
