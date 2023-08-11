import Card from 'react-bootstrap/Card';
import recipeImg from './recipeImg.png'
// 받은 정보로 채워주기
function CardComponent({recipe}) {
  console.log('card component 반복 ')
  return (
    <Card style={{ width: '16rem'}}>
      <Card.Img variant="top" src={recipeImg} />
      <Card.Body>
        <Card.Title>레시피 제목</Card.Title>
        <Card.Text>
          {/* {recipe.description}  */}
          설명
        </Card.Text>
        <Card.Text>
          {/* <span> ❤ 좋아요 수 {recipe.hit} | 등록 날짜 : {recipe.writtenTime} </span> */}
          <span> ❤ 좋아요 수 | 등록 날짜 :  </span>
        </Card.Text>
      </Card.Body>
    </Card>
  );
}

export default CardComponent;