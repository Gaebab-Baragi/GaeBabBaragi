import './PetListPage.css'
import React, { useEffect, useState,useRef} from "react";
import { Swiper, SwiperSlide } from 'swiper/react';

import 'swiper/css';
import 'swiper/css/effect-coverflow';
import 'swiper/css/pagination';
import { EffectCoverflow, Pagination, Navigation, Mousewheel } from 'swiper/modules';
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
    <div>
      <Swiper
        effect={'coverflow'}
        grabCursor={true}
        centeredSlides={true}
        slidesPerView={3}
        coverflowEffect={{
          rotate: 50,
          stretch: 0,
          depth: 100,
          modifier: 1,
          slideShadows: false,
        }}
        style={{width:"80%"}}
        pagination={true}
        // navigation={{nextEl:'.swiper-button-next', prevEl:'.swiper-button-prev', clickable:true,}}
        mousewheel={true}
        modules={[EffectCoverflow, Pagination, Navigation]}
        className="mySwiper"
      >

      {petList.map((petInfo)=>{
        return(
          <SwiperSlide style={{width:"400px" }}>
            <PetRegisterForm pet={petInfo}/>
          </SwiperSlide>
        )
      })}

      </Swiper>
    </div>

  )
}

export default PetListPage;