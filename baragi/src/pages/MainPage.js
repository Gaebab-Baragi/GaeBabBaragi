import BackGround from "../components/ui/BackGround";
import CardCarousel from "../components/list/CardCarousel";

function MainPage(){
    return(
        <div>
            <BackGround></BackGround>
            <h2>추천레시피</h2>
            <CardCarousel/>
        </div>
    )
}
export default MainPage;