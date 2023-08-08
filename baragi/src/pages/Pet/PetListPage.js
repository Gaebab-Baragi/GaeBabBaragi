import './PetListPage.css'
import React, { useEffect, useState } from "react";
import { Swiper, SwiperSlide } from 'swiper/react';

import 'swiper/css';
import 'swiper/css/effect-coverflow';
import 'swiper/css/pagination';
import { EffectCoverflow, Pagination, Navigation } from 'swiper/modules';
import axios from 'axios';
import PetRegisterForm from '../../components/form/PetRegisterForm';

function PetListPage() {
  const [petList, setPetList] = useState([])

  useEffect(()=>{
    axios.get(`/api/pet`)
    .then((res)=>{
      if (res.status === 200){
        console.log('pet list : ' , res.data)
        setPetList(res.data)
      }
    })
    .catch((err)=>{
      console.log('error : ' , err)
    })
  },[])

  return(
    <div className='pageContainer'>
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
        style={{width:"80%"}}
        pagination={true}
        mousewheel={true}
        modules={[EffectCoverflow, Pagination, Navigation]}
        className="mySwiper"
        slideToClickedSlide={true}
        noSwipingClass='react-tags__listbox-option'
      >
      {petList.map((petInfo, index)=>{
        return(
          <SwiperSlide style={{width:"400px" }} key= {index}>
            <PetRegisterForm className='swiper-no-swiping' petInfo={petInfo} idx={index}/>
          </SwiperSlide>
        )
      })}
        <SwiperSlide style={{width:"400px" }} key= "add">
          <PetRegisterForm className='swiper-no-swiping' idx={petList.length}/>
        </SwiperSlide>
      </Swiper>
    </div>

  )
}

export default PetListPage;