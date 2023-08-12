import Card from 'react-bootstrap/Card';
import recipeImg from './recipeImg.png'
import { useNavigate } from 'react-router-dom';

function CardComponent({recipe}) {
  const navigate = useNavigate();
  const handleNavigateDetail = ()=> {
    navigate(`/recipe-detail/${recipe.id}`)
  }
  
  return (
    <Card onClick={handleNavigateDetail} style={{ width: '16rem'}}>
      <Card.Img style={{height:'200px'}} variant="top" src={recipe.imgUrl} />
      <Card.Body>
        <Card.Title>{recipe.title}</Card.Title>
        <Card.Text>
          {recipe.description} 
        </Card.Text>
        <Card.Text style={{display:'flex', justifyContent:'space-around'}}>
          <span> ❤  {recipe.hit}  </span>
          <span> | </span>
          <span > 작성자 : {recipe.member.nickname} </span>
        </Card.Text>
      </Card.Body>
    </Card>
  );
}

export default CardComponent;