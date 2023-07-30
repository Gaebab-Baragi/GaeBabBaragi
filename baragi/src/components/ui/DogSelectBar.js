import { useSelector } from 'react-redux';
import './DogSelectBar.css'
import dogImg from './dogExample.jpg'
import { useDispatch } from 'react-redux';
import { updateDogs } from '../../redux/searchRecipeSlice';
import { useEffect, useState } from 'react';

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

  
  const fakeDogId = [1,2,3]

  return(
    <div className="dogContainer">
      <div className='dogContainerHeader'>
        <span className='dogContainerTitle'>내 반려동물</span>
        <button>+</button>
      </div>
      <hr className='dogContainerSeperator'/>
      <div className='dogImageContainer'>
        {/* 가지고 있는 강아지 정보를 map하면서  */}
        {
          fakeDogId.map((fake)=>{
            return(
              <img className="dogImage" onClick={()=>handleSelectDog(fake)}  src={dogImg} />
            )
          })
        }

      </div>
    </div>
  )
}

export default DogSelectBar;