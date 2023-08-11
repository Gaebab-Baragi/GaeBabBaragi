import MyBookmarks from "../components/ui/MyPage/MyBookmarks";
import "./css/MyInfo.css"
import MyStreamings from "../components/ui/MyPage/MyStreamings";
import MyRecipes from "../components/ui/MyPage/MyRecipes"
import MyPageNavBar from "../components/ui/MyPage/MyPageNavbar";

function MyRecipePage() {
    return (
        <>
            <MyPageNavBar></MyPageNavBar>
            <div className="myInfoSection">
                <h2 className="myInfoHeader">내 방송</h2> 
                <MyStreamings></MyStreamings>
            </div>       
            <hr/>
            <div className="myInfoSection">
                <h2 className="myInfoHeader">내가 쓴 레시피</h2> 
                <MyRecipes rowNum={2}></MyRecipes>
            </div>
            <hr/>
            <div className="myInfoSection">
                <h2 className="myInfoHeader">내가 좋아하는 레시피</h2>
                <MyBookmarks rowNum={2}></MyBookmarks> 
            </div>
        </>
      );
    }
export default MyRecipePage;