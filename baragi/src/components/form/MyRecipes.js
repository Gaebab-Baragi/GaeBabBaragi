import axios from "axios";
import { useEffect, useState } from "react";
import RecipeCardPagination from "../list/RecipeCardPagination";


const MyRecipes =() =>{
    return (
        <>
            <div>
                내가 쓴 레시피
                <RecipeCardPagination api="/api/recipes/writer"></RecipeCardPagination>
            </div>
            <div>
                내가 좋아요 누른 레시피
            </div>       
        
        
        
        </>
    )
}

export default MyRecipes;