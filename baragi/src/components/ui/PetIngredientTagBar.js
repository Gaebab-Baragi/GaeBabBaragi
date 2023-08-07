import React, {useCallback, useEffect, useState} from "react";
import { ReactTags } from "react-tag-autocomplete";
import './PetIngredientTagBar.css'
import { useDispatch } from "react-redux";

function PetIngredientTagBar() {
  const [selected, setSelected] = useState([]);
  const dispatch = useDispatch();

  // 재료 리스트 받아와주기
  const suggestions = [
    {value:1, label:'고구마'},
    {value:2, label:'감자'},
    {value:3, label:'소고기'},
    {value:4, label:'돼지고기'}
  ]

  const onAdd = useCallback(
    (newTag) => {
      setSelected([...selected, newTag])
    },
    [selected]
  )

  const onDelete = useCallback(
    (tagIndex) => {
      setSelected(selected.filter((_, i) => i !== tagIndex))
    },
    [selected]
  )


  return(
    <div className="ingredientSelect">
      <ReactTags
        suggestions={suggestions}
        placeholderText="재료 선택"
        selected={selected}
        onAdd={onAdd}
        onDelete={onDelete}
        noOptionsText="일치하는 재료가 없습니다."
        allowBackspace={true}

      />
    </div>
  );
};

export default PetIngredientTagBar;