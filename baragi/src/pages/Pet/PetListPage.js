import './PetListPage.css'
import React, { useEffect, useState } from "react";
import { Swiper, SwiperSlide} from 'swiper/react';

import 'swiper/css';
import 'swiper/css/effect-coverflow';
import 'swiper/css/pagination';
import { EffectCoverflow, Pagination, Navigation } from 'swiper/modules';
import axios from 'axios';
import PetRegisterForm from '../../components/form/PetRegisterForm';
import { useParams } from 'react-router-dom';

function PetListPage() {
  const [petList, setPetList] = useState([])
  const { idx } = useParams();

  useEffect(()=>{
    axios.get(process.env.REACT_APP_BASE_URL +`/api/pet`)
    .then((res)=>{
      if (res.status === 200){
        console.log('pet list : ' , res.data)
        setPetList(res.data)
      }
    })
    .catch((err)=>{
      console.log('error : ' , err)
    })
  },[idx])

  return(
    <div className='myswiper'>
      <Swiper
        effect={'coverflow'}
        grabCursor={true}
        centeredSlides={true}
        slidesPerView={"auto"}
        coverflowEffect={{
          rotate: 50,
          stretch: 0,
          depth: 100,
          modifier: 1,
          slideShadows: false,
        }}
        style={{width:"100%", paddingTop:"3%", paddingBottom:"3%"}}
        pagination={true}
        mousewheel={true}
        modules={[EffectCoverflow, Pagination, Navigation]}
        slideToClickedSlide={true}
        noSwipingClass='react-tags__listbox-option'
        initialSlide={idx}
        autoHeight={true}
      >
      
      
      {petList.map((petInfo, index)=>{
        return(
          <SwiperSlide style={{width:"400px" }} key= {index +1}>
            <PetRegisterForm className='swiper-no-swiping' petInfo={petInfo} idx={index + 1}/>
          </SwiperSlide>
        )
      })}
        <SwiperSlide style={{width:"400px" }} key= {petList.length + 1}>
          <PetRegisterForm className='swiper-no-swiping' idx={petList.length +1 }/>
        </SwiperSlide>
      </Swiper>
    </div>

  )
}

export default PetListPage;