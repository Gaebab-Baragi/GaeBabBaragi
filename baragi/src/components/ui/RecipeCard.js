import Card from 'react-bootstrap/Card';

function CardComponent({item}) {
  return (
    <Card style={{ width: '16rem'}}>
      <Card.Img variant="top" src={item.imgUrl} />
      <Card.Body>
        <Card.Title>{item.title}</Card.Title>
        <Card.Text>
          {item.description}
        </Card.Text>
        <Card.Text>
          <span>조회수 : {item.hit} | ❤ : {item.bookmarkCount} | {item.regDate} </span>
        </Card.Text>
      </Card.Body>
    </Card>
  );
}

export default CardComponent;