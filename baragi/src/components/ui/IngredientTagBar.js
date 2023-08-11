import React, {useCallback, useEffect, useState} from "react";
import { ReactTags } from "react-tag-autocomplete";
import './IngredientTagBar.css'
import { useDispatch } from "react-redux";
import { updateIngredients, requestFilteredRecipeList } from "../../redux/recipeSearchSlice";
import useDidMountEffect from "../../useDidMountEffect";
import axios from "axios";

function IngredientTagBar() {
  const [suggestions, setSuggestions] = useState('');
  const [selected, setSelected] = useState([]);
  const dispatch = useDispatch();

  useDidMountEffect(()=>{
    dispatch(updateIngredients(selected))
  }, [selected])

  // useDidMountEffect(()=>{
  //   dispatch(requestFilteredRecipeList())
  // }, [selected])

  // 재료 리스트 받아와주기
  useEffect(()=>{
    axios.get(process.env.REACT_APP_BASE_URL +'/api/ingredients')
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
        placeholderText={selected.length? "" : "재료 선택"}
        selected={selected}
        onAdd={onAdd}
        onDelete={onDelete}
        noOptionsText="일치하는 재료가 없습니다."
        allowBackspace={true}

      />
    </div>
  );
};

export default IngredientTagBar;