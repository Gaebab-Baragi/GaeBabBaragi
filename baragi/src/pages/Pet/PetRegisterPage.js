import './PetRegisterPage.css'
import React, {useCallback, useEffect, useState,useRef} from "react";
import PetRegisterForm from '../../components/form/PetRegisterForm';

function PetRegisterPage() {

  return(
    <div className='petRegisterContainer'>
      <PetRegisterForm/>
    </div>

  )
}

export default PetRegisterPage;