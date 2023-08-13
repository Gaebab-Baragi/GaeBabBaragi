import './css/MainPage.css';
import HeroSection from "../components/ui/mainPage/HeroSection";
import ServiceInfo from '../components/ui/mainPage/ServiceInfo';
import { useEffect } from 'react';
import axios from 'axios';

function MainPage(){
    useEffect(() => {
        axios.get(process.env.REACT_APP_BASE_URL +"/api/checkLogin")
        .then((res) => {
            console.log("")
        })
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