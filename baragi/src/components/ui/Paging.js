import Pagination from 'react-js-pagination';
import './Paging.css'




export const Paging = ({ page, count, setPage }) => {

  if (count === 0 || count === undefined) {
    // If there are no items or count is undefined, do not render pagination
    return null;
  }



  return (
    <Pagination
      activePage={page}
      totalItemsCount={count}
      pageRangeDisplayed = {5} // 조건문 count 가  이 되면 diplay 를 4로 하고싶어
      prevPageText={'‹'}
      nextPageText={'›'}
      onChange={setPage}
    />
  );
};

export default Paging;



