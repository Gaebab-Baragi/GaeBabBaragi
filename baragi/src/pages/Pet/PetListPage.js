import './PetListPage.css'
import React, { useEffect, useState,useRef} from "react";
import { Swiper, SwiperSlide } from 'swiper/react';

import 'swiper/css';
import 'swiper/css/effect-coverflow';
import 'swiper/css/pagination';
import { EffectCoverflow, Pagination, Navigation, Mousewheel } from 'swiper/modules';
import axios from 'axios';
import { useSelector } from 'react-redux';
import PetListForm from '../../components/form/PetListForm';
function PetListPage() {
  const [petList, setPetList] = useState([])
  const user = useSelector(state=>state.user)

  useEffect(()=>{
    axios.get(`/api/pet?member_id=${user.id}`)
    .then((res)=>{
      console.log('pet list : ' , res.data)
      setPetList(res.data)
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

      {petList.map((pet)=>{
        return(
          <SwiperSlide style={{width:"400px" }}>
            <PetListForm pet={pet}/>
          </SwiperSlide>
        )
      })}

      </Swiper>
    </div>

  )
}

export default PetListPage;