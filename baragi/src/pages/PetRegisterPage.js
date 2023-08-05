import './PetRegisterPage.css'
import React, {useCallback, useEffect, useState,useRef} from "react";
import PetRegisterForm from '../components/form/PetRegisterForm';
import { Swiper, SwiperSlide } from 'swiper/react';
import 'swiper/css';
import 'swiper/css/effect-coverflow';
import 'swiper/css/pagination';
import { EffectCoverflow, Pagination } from 'swiper/modules';

function PetRegisterPage() {
  

  return(
    <div className="pageContainer">
      <Swiper
        effect={'coverflow'}
        grabCursor={true}
        centeredSlides={true}
        slidesPerView={'auto'}
        coverflowEffect={{
          rotate: 50,
          stretch: 0,
          depth: 100,
          modifier: 1,
          slideShadows: true,
        }}
        pagination={true}
        modules={[EffectCoverflow, Pagination]}
        className="mySwiper"
      >
        <SwiperSlide>
          <PetRegisterForm/>
        </SwiperSlide>
        <SwiperSlide>
          <PetRegisterForm/>
        </SwiperSlide>
      </Swiper>
      
    </div>
  )
}

export default PetRegisterPage;