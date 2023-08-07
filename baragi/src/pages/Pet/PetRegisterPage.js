import './PetRegisterPage.css'
import React, {useCallback, useEffect, useState,useRef} from "react";
import PetRegisterForm from '../../components/form/PetRegisterForm';
import { useParams } from 'react-router';

function PetRegisterPage() {
  const {id} = useParams();

  return(
    <div className='petRegisterContainer'>
      <PetRegisterForm petId={id}/>
    </div>
  )
}

export default PetRegisterPage;