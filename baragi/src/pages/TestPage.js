import SearchBar from "../components/ui/SearchBar";
import BookData from './Data.json'
import IngredientTagBar from "../components/ui/IngredientTagBar";
import './TestPage.css'

function TestPage() {
  return(
    <div className="searchContainer"> 
      {/* 레시피 제목 목록을 넘겨줌 */}
      <SearchBar data={BookData}/>
      <div className="marginBetweenTags" />
      <IngredientTagBar/>
    </div>
  )
};

export default TestPage;