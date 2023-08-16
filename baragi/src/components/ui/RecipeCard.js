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

  const truncateText = (text, maxLength) => {
    if (text.length > maxLength) {
      return text.slice(0, maxLength) + '...';
    }
    return text;
  };

  return (
    <Card style={{ width: '16.5rem', cursor : 'pointer',height: '400px' }} onClick={onClick}>
      <Card.Img style={{height:'200px'}}  variant="top" src={item.recipeImageUrl} />
      <Card.Body  style={{height:'auto'}}> 
        <Card.Title>{item.title}</Card.Title>
        <Card.Text style={{textAlign:'left'}}>
        {truncateText(item.description, 70)}
        </Card.Text>
      </Card.Body>
      <Card.Text style={{ marginBottom: '5%', display: 'flex', justifyContent: 'space-around', alignItems: 'center' }}>
        <span style={{ display: 'flex', alignItems: 'center' }}>
          <ion-icon name="eye" size="small" style={{ marginRight: '5px' }}></ion-icon>
          {item.hit}
        </span>
        <span>|</span>
        <span style={{ display: 'flex', alignItems: 'center' }}>
          <ion-icon name="heart" size="small" style={{ marginRight: '5px' }}></ion-icon>
          {bookMarks}
        </span>
        <span>|</span>
        <span style={{ display: 'flex', alignItems: 'center' }}>
        <ion-icon name="calendar-clear-sharp" size='small' style={{ marginRight: '5px' }}></ion-icon>
          {item.writtenTime.toString().slice(0,10)}
        </span>
      </Card.Text>
    </Card>
  );
}

export default RecipeCard;