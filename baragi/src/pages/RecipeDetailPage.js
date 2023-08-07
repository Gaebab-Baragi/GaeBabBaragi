// import Comment from "../components/form/LoginForm";
import { async } from 'q';
import React, {useState,useEffect} from 'react';
import { useParams } from 'react-router-dom';
import '../components/form/css/RecipeDetail.css';
const RecipeDetailPage=()=>{
    const {id} =useParams();
    const [data,setData]=useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [isLiked, setIsLiked] = useState(false);

    useEffect(()=>{
        const fetchData=async()=>{
            try{
                const response =await fetch(`/api/recipes/${id}`);
                if(!response.ok){
                    throw new Error('request fail!!!!!!!!!!!');
                }
                const data=await response.json();
                setData(data);
            }catch(error){
                console.error('Error occured ',error);
            }
        };
        fetchData();
    },[id]);

    useEffect(() => {
        // 여기서 로그인 상태를 체크하고 isLoggedIn 상태 업데이트
        // 예시로 임의로 로그인 상태를 true로 설정한다고 가정
        setIsLoggedIn(true);
      }, []);

    const handleLikeClick = () => {
        setIsLiked((prevIsLiked) => !prevIsLiked);
    };

    if(!data){
        return <div> Loading ...</div>;
    }
    return (
    <div className='detail'>
        <div className='detailForm'>
            <h1>{data.title}</h1>
            <div className="hit">
                {/* 로그인 상태와 좋아요 여부에 따라 아이콘 표시 */}
                {isLoggedIn && !isLiked ? (
                <ion-icon name="heart-outline" onClick={handleLikeClick}></ion-icon>
                ) : (
                <ion-icon name="heart" onClick={handleLikeClick}></ion-icon>
                )}
                <span>10</span>
                <ion-icon name="eye-outline" ></ion-icon>
                <span>{data.hit}</span>
            </div>
            <div className='imgForm'>
                <img className='imgsize' src={data.imgUrl}></img>
            </div>
            <div>ㄻ널마ㅣ;ㄴ럼;ㄴㅇ</div>
        </div>
    </div>
    );
};
export default RecipeDetailPage;