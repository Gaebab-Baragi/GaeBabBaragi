import React, {useCallback, useEffect, useState} from "react";
import { ReactTags } from "react-tag-autocomplete";
import './PetIngredientTagBar.css'
import { useDispatch } from "react-redux";
import { setForbiddenIngredients } from "../../redux/petRegisterSlice";
import axios from "axios";

function PetIngredientTagBar({forbiddens, selectIngredients}) {
  const [selected, setSelected] = useState([]);
  const [suggestions, setSuggestions] = useState('');
  const dispatch = useDispatch();

  useEffect(()=>{
    console.log("dkk", forbiddens)
    if (forbiddens) {
      const tmp = []
      forbiddens.map((i)=>{
        tmp.push(i.ingredientName)
      })
      console.log(tmp)
      setSelected(tmp)
    }
  },[])

  // 재료 리스트 받아와주기
  useEffect(()=>{
    axios.get('/api/ingredients')
    .then((res)=>{
      console.log('res data : ' ,res.data)
      const tmp = []
      res.data.ingredients.map((i)=>{
        tmp.push({value:i.id, label:i.name})
      })
      setSuggestions(tmp)
    })
    .catch((err)=>{
      console.log('error is : ', err)
    })
  },[])

  useEffect(()=>{
    console.log(selected)
    dispatch(setForbiddenIngredients(selected))
    selectIngredients(selected);
  },[selected])

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
    <div className="petIngredientSelect">
      <ReactTags
        suggestions={suggestions}
        placeholderText={selected.length ? '' : "이건 먹으면 안 돼요"}
        selected={selected}
        onAdd={onAdd}
        onDelete={onDelete}
        noOptionsText="일치하는 재료가 없습니다."
        allowBackspace={true}
        inline
      />
    </div>
  );
};

export default PetIngredientTagBar;