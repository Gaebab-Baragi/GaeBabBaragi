import { useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

function RecipeDetailPage() {

    const params = useParams();
    useEffect(() => {

        axios.get(`/api/recipes/${params.id}`)
        .then((res) => {
            if (res.status === 200){
                console.log(res.data);
            }
        })
        .catch((res) => {
            console.log(res);
        })
    }, [])


    return (
        <>
        <div>
            <div>제목 받아오기 예정</div>
            <div>대표사진 예정</div>
            <div>
                <h2>재료</h2>
                <span>재료 리스트 뽑아서 하나씩 예정</span>
            </div>
        </div>
        
        </>
    );
    }
export default RecipeDetailPage;