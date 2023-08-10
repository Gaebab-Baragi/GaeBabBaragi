import './css/MainPage.css';
import HeroSection from "../components/ui/mainPage/HeroSection";
import ServiceInfo from '../components/ui/mainPage/ServiceInfo';
import { useEffect } from 'react';
import axios from 'axios';

function MainPage(){
    useEffect(() => {
        axios.get("/api/checkLogin")
        .then(console.log("hi"))
        .catch((res) => console.log(res));
    }, [])
    return(
        <div className='mainPage'>
            <HeroSection></HeroSection>
            <ServiceInfo></ServiceInfo>
        </div>
    )
}
export default MainPage;