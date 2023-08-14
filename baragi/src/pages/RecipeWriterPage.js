import React, { useState, useEffect } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router-dom'; // 로그인 됏는지 확인해서 리다이렉트 하려고 필요.
import { useSelector } from 'react-redux';
import axios from 'axios';

const RecipeWriterPage = () => {
    const { id } = useParams();
    const [data, setData] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            console.log(id);
            const response = await fetch(process.env.REACT_APP_BASE_URL + `/api/recipes/writer/${id}`);
            const data = await response.json();
            setData(data);
        };
        fetchData();
    }, [id]);

    return (
        <div>
            {data ? (
                <div>
                    <h1>{data.name}님이 작성한 레시피 목록</h1>
                    {data.recipes.map((recipe, index) => (
                        <li key={index}>{recipe.title}</li>
                    ))}
                </div>
            ) : (
                <p>Loading...</p>
            )}
        </div>
    );
};

export default RecipeWriterPage;