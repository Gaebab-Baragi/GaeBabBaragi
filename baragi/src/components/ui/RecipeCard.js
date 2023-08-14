import Card from 'react-bootstrap/Card';
import { useNavigate } from 'react-router-dom';

function RecipeCard({item}) {
  const navigate = useNavigate();

  const onClick = (e) => {
    navigate(`/recipe-detail/${item.id}`);
  }

  return (
    <Card style={{ width: '16rem', cursor : 'pointer'}} onClick={onClick}>
      <Card.Img style={{height:'200px'}}  variant="top" src={item.recipeImageUrl} />
      <Card.Body>
        <Card.Title>{item.title}</Card.Title>
        <Card.Text>
          {item.description}
        </Card.Text>
        <Card.Text  style={{display:'flex', justifyContent:'space-around'}}>
          <span>❤️  {item.hit}</span>
          <span> | </span>
          <span>📅 {item.writtenTime.toString().slice(0,10)}</span>
        </Card.Text>
      </Card.Body>
    </Card>
  );
}

export default RecipeCard;