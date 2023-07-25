import SearchBar from "../components/ui/SearchBar";
import BookData from './Data.json'
import IngredientTagBar from "../components/ui/IngredientTagBar";

function TestPage() {
  return(
    <div>
      <SearchBar data={BookData }/>
      <IngredientTagBar/>
    </div>
  )
};

export default TestPage;