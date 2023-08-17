import React, { useEffect,useState, } from 'react';
import axios from 'axios';
import { useDispatch,useSelector } from 'react-redux';
import { requestFilteredRecipeList,updateVideo, updateRecipeInfor} from '../redux/recipeRegisterSlice';
import  { useLocation, useNavigate,useParams } from 'react-router-dom';
import useDidMountEffect from '../useDidMountEffect'
function RecipeUpdatePage() {
    // const location = useLocation();
    const { id } = useParams();
    // const {  } = location.state;
    const [Data,setData] = useState({});
    const [recipeTitle, setrRecipeTitle] = useState("");
    const [recipeDescription, setRecipeDescription] = useState("");
    const [recipeStepOrderingNumber, setrecipeStepOrderingNumber] = useState("");
    const [recipeStepDescription, setrecipeStepDescription] = useState("");
    const [recipeIngredients, setMaxParticipant] = useState(2);
    const [password, setPassword] = useState(null);
    const user = useSelector(state=>state.user)
    const navigate = useNavigate();
    // 로그인 안된 유저는 접근 안됨
    useEffect(()=>{
      if (!user) {
        alert('로그인 후 이용해주세요.')
        navigate('/login')
      }
    },[])
    
    const { recipeId } = useParams();
    const dispatch = useDispatch();
    const [recipeData, setRecipeData] = useState({}); // 수정할 레시피 정보를 저장하는 상태
  
    useEffect(()=>{
        console.log(process.env.REACT_APP_BASE_URL + `/api/recipes/${id}`)
    axios.put(process.env.REACT_APP_BASE_URL + `/api/recipes/${id}`)
    .then((res)=>{
      console.log('레시피 제목 가져오기', res.data)
      setData(res.data)
    })
    .catch((err)=>{
      console.log('레시피 제목 못 가져옴', err)
    })
  },[])



return (
    <div style={{ marginLeft:'15%' , marginRight : '15%' , marginTop : '0.5%' , marginBottom : '10%'}}>
        <h1 style={{ textAlign : 'left',marginBottom :'1%' }}>레시피 수정</h1>
        <h4 style={{textAlign:'left', marginLeft:'5%'}}>1. 대표사진 등록</h4>
        <img src="/" alt="" />
    <div style={{ marginTop : '1%', marginBottom : '1%' ,  justifyContent:'center', alignItems:'center'}}> 
    </div>
    </div>
)
}

export default RecipeUpdatePage ;





// const navigate = useNavigate();
// const user = useSelector((state)=>state.user)
// const dogs = useSelector((state)=>state.recipeSearch.dogs)
// const ingredients = useSelector((state)=>state.recipeSearch.ingredients)
// const title = useSelector((state)=>state.recipeSearch.title)
// const [filtered, setFiltered] = useState(false);
// const [filteredList, setFilteredList] = useState([])
// const [recipeTitleList, setRecipeTitleList] = useState([])
// const [popularRecipes, setPopularRecipes] = useState([])
// const [showCarousel, setShowCarousel] = useState(false);