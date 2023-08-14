import { useEffect, useState } from 'react';
import Card from 'react-bootstrap/Card';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function RecipeCard({item}) {
  const navigate = useNavigate();
  const [bookMarks, setBookMarks] = useState(0);
  const onClick = (e) => {
    navigate(`/recipe-detail/${item.id}`);
  }

  useEffect(()=>{
    axios.get(process.env.REACT_APP_BASE_URL + `/api/bookmark/${item.id}`)
    .then((res)=>{
        console.log(`${item.id }좋아요 수`,  res.data)
        setBookMarks(res.data)
    })
  },[])

  return (
    <Card style={{ width: '16rem', cursor : 'pointer'}} onClick={onClick}>
      <Card.Img style={{height:'200px'}}  variant="top" src={item.recipeImageUrl} />
      <Card.Body>
        <Card.Title>{item.title}</Card.Title>
        <Card.Text>
          {item.description}
        </Card.Text>
        <Card.Text  style={{display:'flex', justifyContent:'space-around'}}>
          <span>👁‍🗨 {item.hit}</span>
          <span>❤️ {bookMarks}</span>
          {/* <span> | </span> */}
          <span>📅 {item.writtenTime.toString().slice(0,10)}</span>
        </Card.Text>
      </Card.Body>
    </Card>
  );
}

export default RecipeCard;