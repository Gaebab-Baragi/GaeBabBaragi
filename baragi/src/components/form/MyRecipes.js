import axios from "axios";
import { useEffect, useState } from "react";
import RecipeCardPagination from "../list/RecipeCardPagination";
import "./css/MyInfo.css"


const MyRecipes =() =>{
    return (
        <>
            <div className="myInfoSection">
                <h2>내 방송</h2> 
            </div>       
            <hr/>
            <div className="myInfoSection">
                <h2>내가 쓴 레시피</h2> 
                <RecipeCardPagination rowNum='2' api='/api/recipes/writer'></RecipeCardPagination>
            </div>
            <hr/>
            <div className="myInfoSection">
                <h2>내가 좋아하는 레시피</h2>
            </div>       
        </>
    )
}

export default MyRecipes;