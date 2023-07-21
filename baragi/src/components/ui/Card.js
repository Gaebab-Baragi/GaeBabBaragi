import Card from 'react-bootstrap/Card';
import pracImg from './pracImage.jpg'

function CardComponent({count}) {
  return (
    <Card style={{ width: '18rem' }}>
      <Card.Img variant="top" src={pracImg} />
      <Card.Body>
        <Card.Title>Recipe Title</Card.Title>
        <Card.Text>
          Recipe Explanation
          블라블라  
        </Card.Text>
        <Card.Text>
          <span>조회수 : 16 | ❤ : {count} | 날짜 </span>
        </Card.Text>
      </Card.Body>
    </Card>
  );
}

export default CardComponent;