// import Comment from "../components/form/LoginForm";

//npm i react-copy-to-clipboard : 필요 (for 링크 복사)

import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom'; // 로그인 됏는지 확인해서 리다이렉트 하려고 필요.
// import { useParams } from 'react-router-dom';
import { CopyToClipboard } from 'react-copy-to-clipboard'; // Import CopyToClipboard
import '../components/form/css/RecipeDetail.css';
import { useSelector } from 'react-redux';

//링크 복사 함수
const copyUrlToClipboard = () => {
    const currentUrl = window.location.href;
  
    // Perform the copy to clipboard
    if (currentUrl) {
      navigator.clipboard.writeText(currentUrl).then(
        () => {
          // You can show a success message here if needed
          alert('링크가 복사 되었습니다!')
        },
        () => {
          // Handle error if copying fails
          console.error('Copying URL to clipboard failed');
        }
      );
    }
  };


// props로 id 넘겨줄 예정입니다
const RecipeDetailPage=()=>{
    const {id} = useParams();
    const [data,setData]=useState(null);
    const isLoggedIn = useSelector(state => state.user.isLogin);
    const [isLiked, setIsLiked] = useState(false);
    useEffect(()=>{
        const fetchData=async()=>{
            try{
                const response =await fetch(`/api/recipes/${id}`);
                if(!response.ok){
                    throw new Error('request fail!!!!!!!!!!!');
                }
                const data=await response.json();
                console.log(data);
                setData(data);
            }catch(error){
                console.error('Error occured ',error);
            }
        };
        fetchData();
    },[id]);

    //하트 눌렀을 때, 로그인 안되어있으면 로그인 페이지로 리다이렉트
    const navigate = useNavigate(); // Use useNavigate instead of useHistory
    const handleLikeClick = () => {
        console.log('handleLikeClick function called');
        if (!isLoggedIn) {
            alert('로그인이 필요한 서비스입니다.')
            navigate('/login'); // Replace with your actual login page path
        } else {
            setIsLiked((prevIsLiked) => !prevIsLiked);
        }
    };

    if(data){
        console.log();
    }
    
    if(!data){
        return <div> Loading ...</div>;
    }
    return (
    <div className='detail'>
        <div className='detailForm'>
            <h1 className='recipeTitle'>{data.title}</h1>
            <div className="hit">
                <div className="icon-container">
                    {/* 로그인 상태와 좋아요 여부에 따라 아이콘 표시 */}
                    {!isLoggedIn && !isLiked ? (
                    <ion-icon name="heart-outline" onClick={handleLikeClick}></ion-icon>
                    ) : (
                    <ion-icon name="heart" onClick={handleLikeClick}></ion-icon>
                    )}
                    <span>10</span>
                    <ion-icon name="eye-outline"></ion-icon>
                    <span>{data.hit}</span>
                    <CopyToClipboard text={window.location.href}>
                        <ion-icon name="share-social-outline" onClick={copyUrlToClipboard}></ion-icon>
                    </CopyToClipboard>
                </div>
            </div>
            <div className='imgForm'>
                <img className='imgsize' src={data.imgUrl}></img>
                <div>
                    <button>
                        <img className='profileImg' src={data.member.memberImage}></img>
                    </button>
                </div>
                <div className='nickname'>
                    {data.member.nickname}
                </div>
            </div>
            <div className='descriptionForm'>
                {data.description}
            </div>
            <hr></hr>
            <div className="ingredientsForm">
                <h2>재료</h2>
                <ul className="ingredient-list">
                    {data.ingredients.map((ingredient, index) => (
                        <li key={index} className={`ingredient-item ${index % 2 === 0 ? 'even' : 'odd'}`}>
                            <div className="ingredient-info">
                                <div className="ingredient-details">
                                    <div className="ingredient-name">{ingredient.name}</div>
                                    <div className="ingredient-amount">{data.recipeIngredients[index].amount}</div>
                                </div>
                            </div>
                        </li>
                    ))}
                </ul>
            </div>
            <hr></hr>
            <div className='stepForm'>
                <h2>조리 순서</h2>
                <ul className='step-list'>
                    {data.steps.map((step,index)=>(
                        <li key={index} className='step-item'>
                            <div className='step-info'>
                                <div className='step-details'>
                                    <div className='step-ordering'>Step {step.orderingNumber}</div>
                                    <div className='step-description'>{step.description}</div>
                                </div>
                            </div>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    </div>
    );
};
export default RecipeDetailPage;