import { useSelector } from 'react-redux';
import './DogSelectBar.css'
import dogImg from './dogExample.jpg'
import { useDispatch } from 'react-redux';
import { updateDogs, requestFilteredRecipeList } from '../../redux/recipeSearchSlice.js';
import { useEffect, useState } from 'react';
import useDidMountEffect from '../../useDidMountEffect';
import { redirect } from 'react-router-dom';

// button 누르면 반려견 등록 페이지로 navigate 해주는 기능 추가해주기

function DogSelectBar() {
  const dispatch = useDispatch();
  const [dogsSelected, setDogsSelected] = useState([]);

  const handleSelectDog = (dogId)=> {
    if (dogsSelected.includes(dogId)) {
      const updatedDogSelected = dogsSelected.filter((id) => id !== dogId);
      setDogsSelected(updatedDogSelected);
    } else {
      const updatedDogSelected = [...dogsSelected, dogId]
      setDogsSelected(updatedDogSelected);
    }
  }

  useEffect(()=>{
    dispatch(updateDogs(dogsSelected))
    
  }, [dogsSelected])

  useDidMountEffect(()=>{
    dispatch(requestFilteredRecipeList())
  }, [dogsSelected])

  const fakeDogId = [1,2,3]

  return(
    <div className="dogContainer">
      <div className='dogContainerHeader'>
        <span className='dogContainerTitle'>내 반려동물</span>
        <button className='addDogButton' >+</button>
      </div>
      <hr className='dogContainerSeperator'/>
      <div className="dogImageContainer">
        {/* 가지고 있는 강아지 정보를 map하면서  */}
        {fakeDogId.map((fake) => {
          const isSelected = dogsSelected.includes(fake);
          return (
            <img
              className={`dogImage ${isSelected ? 'selected' : ''}`}
              onClick={() => handleSelectDog(fake)}
              src={dogImg}
              key={fake}
            />
          );
        })}
      </div>
    </div>
  )
}

export default DogSelectBar;