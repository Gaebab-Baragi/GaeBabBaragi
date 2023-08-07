import Card from 'react-bootstrap/Card';
import { useNavigate } from 'react-router-dom';

function RecipeCard({item}) {
  console.log(item);
  const navigate = useNavigate();

  const onClick = (e) => {
    navigate(`/recipe-detail/${item.id}`);
  }

  return (
    <Card style={{ width: '16rem', margin : '10rem'}} onClick={onClick}>
      <Card.Img variant="top" src={item.recipeImageUrl} />
      <Card.Body>
        <Card.Title>{item.title}</Card.Title>
        <Card.Text>
          {item.description}
        </Card.Text>
        <Card.Text>
          <span>조회수 : {item.hit} | ❤ : {item.bookmarkCount} | {item.writtenTime} </span>
        </Card.Text>
      </Card.Body>
    </Card>
  );
}

export default RecipeCard;