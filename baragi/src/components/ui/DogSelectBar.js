import { useSelector } from "react-redux";
import "./DogSelectBar.css";
import dogImg from "./dogExample.jpg";
import { useDispatch } from "react-redux";

import {
  updateDogs,
  requestFilteredRecipeList,
  updatePetIds
} from "../../redux/recipeSearchSlice.js";
import { useEffect, useState } from "react";
import useDidMountEffect from "../../useDidMountEffect";
import axios from "axios";

// button 누르면 반려견 등록 페이지로 navigate 해주는 기능 추가해주기

function DogSelectBar() {
  const dispatch = useDispatch();
  const [dogsSelected, setDogsSelected] = useState([]);
  const [pets, setPets] = useState({});
  const handleSelectDog = (dogId) => {
    if (dogsSelected.includes(dogId)) {
      const updatedDogSelected = dogsSelected.filter((id) => id !== dogId);
      setDogsSelected(updatedDogSelected);
    } else {
      const updatedDogSelected = [...dogsSelected, dogId];
      setDogsSelected(updatedDogSelected);
    }
  };
  useEffect(() => {
    axios.get(process.env.REACT_APP_BASE_URL +"/api/pet").then((res) => {
      setPets(res.data);
      console.log(res.data)
    });
  }, []);

  useEffect(() => {
    dispatch(updateDogs(dogsSelected));
  }, [dogsSelected]);

  useDidMountEffect(() => {
    dispatch(requestFilteredRecipeList());
  }, [dogsSelected]);
  

  return (
    <div className="dogContainer">
      <div className="dogContainerHeader">
        <span className="dogContainerTitle">내 반려동물</span>
        <button className="addDogButton">+</button>
      </div>
      <hr className="dogContainerSeperator" />
      <div className="dogImageContainer">
        {/* 가지고 있는 강아지 정보를 map하면서  */}
         { Object.values(pets).map((pet,index) => {
          const isSelected = dogsSelected.includes(pet.id);
          return (
            <img
              className={`dogImage ${isSelected ? "selected" : ""}`}
              onClick={() => handleSelectDog(pet.id)}
              src={pet.imgUrl }
              key={index}
            />  
          );
        })} 
      </div>
    </div>
  );
}

export default DogSelectBar;
