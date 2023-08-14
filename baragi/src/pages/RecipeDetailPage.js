// import Comment from "../components/form/LoginForm";

//npm i react-copy-to-clipboard : 필요 (for 링크 복사)

import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom'; // 로그인 됏는지 확인해서 리다이렉트 하려고 필요.
import { CopyToClipboard } from 'react-copy-to-clipboard'; // Import CopyToClipboard
import '../components/form/css/RecipeDetail.css';
import { useSelector } from 'react-redux';
import axios from 'axios';
import StreamingCardComponent from '../components/ui/StreamingCard';

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


const RecipeDetailPage=()=>{
    const { id } = useParams();
    const [data, setData] = useState(null);
    const [bookmarkCnt, setBookmarkCnt] = useState(0);

    const userId=useSelector(state=>state.user.id);

    const isLoggedIn = useSelector(state => state.user.isLogin);
    const [isLiked, setIsLiked] = useState(false);
    // const [reservedRecipe, setReservedRecipe] = useState(null); // State to hold the reserved recipe info
    const navigate = useNavigate(); // Move the navigate hook to the top
    // const location = useLocation(); // useLocation 훅을 이용해 location 변수 가져오기
    const [comments, setComments] = useState([]);
    const [meetings,setMeetings]=useState([]);
    

    // useEffect(() => {
    //     if (isLoggedIn && reservedRecipe && location.pathname === '/login') {
    //         navigate(`/recipe/${reservedRecipe.id}`);
    //         setReservedRecipe(null);
    //     }
    // }, [isLoggedIn, reservedRecipe, location]);


    useEffect(()=>{
        const fetchData=async()=>{
            try{
                const response =await fetch(process.env.REACT_APP_BASE_URL+`/api/recipes/${id}`);
                const responseComment=await fetch(process.env.REACT_APP_BASE_URL+`/api/comment?recipe_id=${id}`,{'Content-Type': 'application/json'})
                const responseMeetings=await fetch(process.env.REACT_APP_BASE_URL+`/api/meetings?recipe_id=${id}`);
                const meetingRooms=await responseMeetings.json();
                setMeetings(meetingRooms);
                if(isLoggedIn==true){
                    const responseIsbookmark=await axios.get(process.env.REACT_APP_BASE_URL+`/api/bookmark/islike/${id}`);
                    const bookmarkdata = responseIsbookmark.data; // 변경된 부분
                    if (bookmarkdata.flag === 1) {
                        setIsLiked(true);
                    }
                }
                const responseBookmark=await fetch(process.env.REACT_APP_BASE_URL+`/api/bookmark/${id}`)
                setBookmarkCnt()
                if(!response.ok){
                    console.log('에러에러 error: ');
                }
                const data=await response.json();

                const comment=await responseComment.json(); 
                setComments(comment);

                const bookmarkCnt=await responseBookmark.json();

                setBookmarkCnt(bookmarkCnt);
                if(data.statusCode==400){
                    alert(data.errorMessage);
                    navigate('/'); // 메인 페이지로 리다이렉트
                    return; // 리다이렉트 후 함수 종료
                }
                console.log(data);
                setData(data);
            }catch(error){
                console.error('Error occured ',"문제야 문제");
            }
        };
        fetchData();
    },[id]);

    const formatDate = (dateString) => {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');
        return `${year}-${month}-${day} ${hours}:${minutes}`;
    };

    const handleStreamingReservation = () => {
        if (!isLoggedIn) {
            alert("로그인이 필요한 서비스입니다.");
            navigate('/login');
        } else {
            navigate(`/streaming-register/${id}`, { state: { recipeTitle: data.title } });
        }
    };

    //하트 눌렀을 때, 로그인 안되어있으면 로그인 페이지로 리다이렉트
    const handleLikeClick = async () => {
        console.log('handleLikeClick function called');
        if (!isLoggedIn) {
            alert('로그인이 필요한 서비스입니다.')
            navigate('/login'); // Replace with your actual login page path
        } else {
            try {
                const response = await axios.post(
                    `${process.env.REACT_APP_BASE_URL}/api/bookmark/${id}`,
                    null,
                    {
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    }
                );
    
                if (response.status === 200) {
                    setIsLiked((prevIsLiked) => !prevIsLiked);
                    setBookmarkCnt(prevBookmarkCnt =>
                        isLiked ? prevBookmarkCnt - 1 : prevBookmarkCnt + 1
                    );
                } else {
                    console.error('좋아요 요청 실패');
                }
            } catch (error) {
                console.error('에러 발생', error);
            }
        }
    };
    // 댓글 쓰기 이벤트
    const [newCommentContent, setNewCommentContent] = useState('');
    const [commentImage, setCommentImage] = useState(null);
    const handleCommentChange = (event) => {
        setNewCommentContent(event.target.value);
    };

    const handleImageChange = (event) => {
        setCommentImage(event.target.files[0]);
    };

    const handleSubmitComment = async (event) => {
        
        event.preventDefault();
        const formData = new FormData();
        const comment={
            recipe_id:{id}.id,
            content:newCommentContent,
        }
        formData.append('dto',new Blob([JSON.stringify(comment)],{type:"application/json"}));
        if(commentImage){
            formData.append('commentImg', new Blob([commentImage]));
        }else{
            formData.append('commentImg',new Blob());
        }
        
        if (!isLoggedIn) {
            alert('로그인이 필요한 서비스입니다.');
            navigate('/login');
            return;
        }
        if (!newCommentContent.trim()) {
            alert('댓글 내용을 작성해주세요');
            return;
        }
        try {
            const response = await axios.post(
                `${process.env.REACT_APP_BASE_URL}/api/comment`,
                formData,
                {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                      }
                }
            );
            if (response.status === 200) {
                // 댓글 작성 후 댓글 목록을 다시 가져온다.
                const responseComment = await axios.get(
                    `${process.env.REACT_APP_BASE_URL}/api/comment?recipe_id=${id}`
                );
                const comment = responseComment.data;
                setComments(comment);
    
                // 댓글 작성 내용 초기화
                setNewCommentContent('');
                setCommentImage(null);
            } else {
                console.error('댓글 작성 실패');
            }
        } catch (error) {
            console.error('에러 발생', error);
        }
    };
    









    //댓글 등록 끝



    //댓글 삭제 시작
    const handleDeleteComment = async (commentId) => {
        try {
            const response = await axios.delete(
                `${process.env.REACT_APP_BASE_URL}/api/comment/${commentId}`
            );
    
            if (response.status === 200) {
                // 댓글 삭제 후 댓글 목록을 다시 가져온다.
                const responseComment = await axios.get(
                    `${process.env.REACT_APP_BASE_URL}/api/comment?recipe_id=${id}`
                );
                const updatedComments = responseComment.data;
                setComments(updatedComments);
            } else {
                console.error('댓글 삭제 실패');
            }
        } catch (error) {
            console.error('에러 발생', error);
        }
    };
    const goToWriterRecipe=(memberId)=>{
        navigate(`/recipe-writer/${memberId}`);
    }




    //댓글 쓰기 이벤트 끝
    
    if(!data){
        return <div> Loading ...</div>;
    }
    return (
    <div className='recipe-container'>
        <div className='detail'>
            <div className='detailForm'>
                <h1 className='recipeTitle'>{data.title}</h1>
                <div className="hit">
                    <div className="icon-container">
                        {/* 로그인 상태와 좋아요 여부에 따라 아이콘 표시 */}
                        {isLoggedIn && isLiked ? (
                            <ion-icon name="heart" onClick={handleLikeClick}></ion-icon>
                            ) : (
                            <ion-icon name="heart-outline" onClick={handleLikeClick}></ion-icon>
                        )}
                        <span>{bookmarkCnt}</span>
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
                        <button onClick={()=>goToWriterRecipe(data.member.memberId)}>
                        {/* onClick={() => handleDeleteComment(comment.id)} */}
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
                        {data.steps.map((step, index) => (
                        <li key={index} className='step-item'>
                            <div className='step-info'>
                                <div>
                                    <img
                                        className='step-img'
                                        src={step.stepImage}
                                        alt="이미지가 없어요"
                                    />
                                </div>
                                <div className='step-details'>
                                    <div className='step-ordering'>Step {step.orderingNumber}</div>
                                    <div className='step-description'>{step.description}</div>
                                </div>
                            </div>
                        </li>
                        ))}
                    </ul>
                </div>
                <hr></hr>
                <div className='commentForm'>
                    <h2>댓글</h2>
                    <ul className='comment-list'>
                        {comments.map((comment, index) => (
                        <li key={index}>
                            <div className='comment-container'>
                                <div className='comment-profile-img'>
                                    <img src={comment.profileUrl}></img>
                                </div>
                                <div className='comment-form-content'>
                                    <div className='comment-info'>
                                        <div className='comment-writer-info'>
                                            {comment.writer}
                                            
                                        </div>
                                        <div className='comment-writtenTime'>
                                            {formatDate(comment.writeTime)}
                                        </div>
                                    </div>
                                        <div className='comment-content-delete'>
                                            <div className='comment-content'>{comment.content}</div>
                                        </div>
                                        {comment.commentImgUrl && (
                                            <div className='comment-img'>
                                                <img className='comment-content-img' src={comment.commentImgUrl}></img>
                                            </div>
                                        )}
                                    {isLoggedIn && comment.writerId === userId&& (
                                            <div className='comment-delete' onClick={() => handleDeleteComment(comment.id)}>삭제하기</div>
                                        )}
                                </div>
                            </div>
                            <hr></hr>
                        </li>
                        ))}
                    </ul>
                                            
                        <div className='comment-form'>
                            <form onSubmit={handleSubmitComment} className="comment-input-container">
                                <div className="comment-input">
                                    <textarea
                                        placeholder='댓글을 입력하세요...'
                                        value={newCommentContent}
                                        onChange={handleCommentChange}
                                    />
                                    <div className='file-input'>
                                        <label>
                                            이미지/동영상<br></br>첨부하기
                                            <input type='file' onChange={handleImageChange} style={{ display: 'none'} } />
                                        </label>
                                    </div>                  
                                    <button type='submit'>댓글 작성</button>
                                </div>
                            </form>
                        </div>


                </div>

            </div>
        
    </div>
    <div className='floatingDiv'>
        <div><ion-icon name="radio-outline" onClick={handleStreamingReservation}></ion-icon></div>
        <div>
            <div className='floatingDiv-Text' onClick={handleStreamingReservation}>
                스트리밍<br></br>예약하기
            </div>
        </div>
        <hr></hr>
        <div>
            <ul className='meeting-list'>
                {meetings.map((meeting, index) => (
                    <li key={index}>
                        <div>
                            <img className='floatingDiv-image' src={meeting.recipe_image_url}></img>
                        </div>
                        <div className='floatingDiv-meeting-info'>
                            <div className='floatingDiv-meeting-host'>
                                <img className='floatingDiv-meeting-host-img' src={meeting.host_profile_url}></img>
                                {meeting.host_nickname}
                            </div>
                            • 예약 시간 : {meeting.start_time}
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