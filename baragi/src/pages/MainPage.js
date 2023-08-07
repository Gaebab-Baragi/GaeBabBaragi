import './MainPage.css';
import HeroSection from "../components/ui/mainPage/HeroSection";
import ServiceInfo from '../components/ui/mainPage/ServiceInfo';
import CardCarousel from "../components/list/CardCarousel";

function MainPage(){
    return(
        <div className='mainPage'>
            <HeroSection></HeroSection>
            <ServiceInfo></ServiceInfo>
            <h2>추천레시피</h2>
            <CardCarousel/>
        </div>
    )
}
export default MainPage;