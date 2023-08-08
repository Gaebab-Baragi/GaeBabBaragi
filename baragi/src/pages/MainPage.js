import './MainPage.css';
import HeroSection from "../components/ui/mainPage/HeroSection";
import ServiceInfo from '../components/ui/mainPage/ServiceInfo';

function MainPage(){
    return(
        <div className='mainPage'>
            <HeroSection></HeroSection>
            <ServiceInfo></ServiceInfo>
        </div>
    )
}
export default MainPage;