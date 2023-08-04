import './PetRegisterPage.css'
import React, {useCallback, useEffect, useState,useRef} from "react";
import PetRegisterForm from '../components/form/PetRegisterForm';
import { Swiper, SwiperSlide } from 'swiper/react';

import 'swiper/css';
import 'swiper/css/effect-coverflow';
import 'swiper/css/pagination';
import { EffectCoverflow, Pagination, Navigation, Mousewheel } from 'swiper/modules';
import { PersistGate } from 'redux-persist/integration/react';

function PetRegisterPage() {
  

  return(
    <div>
      <Swiper
        effect={'coverflow'}
        grabCursor={true}
        centeredSlides={true}
        slidesPerView={'auto'}
        coverflowEffect={{
          rotate: 10,
          stretch: 0,
          depth: 100,
          modifier: 1,
          slideShadows: true,
        }}
        pagination={true}
        navigation={true}
        mousewheel={true}
        modules={[EffectCoverflow, Pagination, ]}
        className="mySwiper"
      >

      <div>
        <SwiperSlide>
          <PetRegisterForm/>
        </SwiperSlide>
        <SwiperSlide>
          <div className='petForm'>
            <PetRegisterForm/>
          </div>
        </SwiperSlide>
        <SwiperSlide>
          <div className='petForm'>
            <PetRegisterForm/>
          </div>
        </SwiperSlide>
      </div>
      </Swiper>
    </div>

  )
}

export default PetRegisterPage;