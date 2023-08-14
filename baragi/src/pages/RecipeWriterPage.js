import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import CardComponent from '../components/ui/Card';
import { styled } from 'styled-components';
import './css/RecipeWriterPage.css'
const CardContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  margin-left: 10%;
  margin-right: 10%;
  justify-content:flex-start;
`;

const StyledCardWrapper = styled.div`
  margin-bottom: 20px;
  margin-right:2.5%
`;

const RecipeWriterPage = () => {
    const { id } = useParams();
    const [data, setData] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
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
                    <h1 className='writer-name'>{data.name}님이 작성한 레시피 목록</h1>
                    <CardContainer>
                        {data.recipes.map((recipe, index) => (
                            <StyledCardWrapper key={recipe.id}>
                                <CardComponent key={index} recipe={recipe} />
                            </StyledCardWrapper>
                        ))}
                    </CardContainer>
                </div>
            ) : (
                <p>Loading...</p>
            )}
        </div>
    );
};

export default RecipeWriterPage;