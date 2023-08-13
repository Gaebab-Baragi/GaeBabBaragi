import MyBookmarks from "../components/ui/MyPage/MyBookmarks";
import "./css/MyInfo.css"
import MyStreamings from "../components/ui/MyPage/MyStreamings";
import MyRecipes from "../components/ui/MyPage/MyRecipes"
import Nav from 'react-bootstrap/Nav';
import { useState } from "react";
import './css/MyRecipePage.css'
import MyPageNavbar from "../components/ui/MyPage/MyPageNavbar.js"

function MyRecipePage() {
  const [currentShow, setCurrentShow] = useState('mystreamings')

    return (
      <>
        <MyPageNavbar sel={3}> </MyPageNavbar>

        <div className="myRecipePageContainer" >
          <Nav justify variant="tabs" defaultActiveKey="/home">
            <Nav.Item>
              <Nav.Link 
              onClick={()=>setCurrentShow('mystreamings')}
              active={currentShow === 'mystreamings'}
              >내 방송</Nav.Link>
            </Nav.Item>
            <Nav.Item>
              <Nav.Link 
              onClick={()=>setCurrentShow('myrecipes')}
              active={currentShow === 'myrecipes'}>내가 쓴 레시피</Nav.Link>
            </Nav.Item>
            <Nav.Item>
              <Nav.Link 
              onClick={()=>setCurrentShow('mybookmarks')}
              active={currentShow === 'mybookmarks'} >내가 찜한 레시피</Nav.Link>
            </Nav.Item>
          </Nav>

          {
            currentShow==='mystreamings'
            ?  <MyStreamings></MyStreamings>
            : currentShow==='myrecipes'? <MyRecipes rowNum={4}/> : <MyBookmarks rowNum={4}/>
          }

        </div>
      </>
      );
    }
export default MyRecipePage;