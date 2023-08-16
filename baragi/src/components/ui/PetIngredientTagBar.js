import React, {useCallback, useEffect, useState} from "react";
import { ReactTags } from "react-tag-autocomplete";
import './IngredientTagBar.css'
import './PetIngredientTagBar.css'
import axios from "axios";
import useDidMountEffect from "../../useDidMountEffect";

function PetIngredientTagBar({forbiddens, selectIngredients}) {
  const [selected, setSelected] = useState([]);
  const [suggestions, setSuggestions] = useState('');
  const [colorArray, setColorArray] = useState([]);

  useEffect(()=>{
    if (forbiddens) {
      const tmp = []
      forbiddens.map((i)=>{
        tmp.push({value : i.ingredientId, label : i.ingredientName})
      })
      setSelected(tmp)
    }
  },[])

  useEffect(()=>{
    const color = () => { return [
      Math.floor(Math.random() * 55 + 200),
      Math.floor(Math.random() * 55 + 200),
      Math.floor(Math.random() * 55 + 200),
    ]
    }
    axios.get(process.env.REACT_APP_BASE_URL +'/api/ingredients')
    .then((res)=>{
      const tmp = []
      res.data.ingredients.map((i)=>{
        tmp.push({value:i.id, label:i.name})
        setColorArray(colorArray => [...colorArray, `rgb(${color()[0]},${color()[1]},${color()[2]})`])
      })
      setSuggestions(tmp)
    })
    .catch((err)=>{
      console.log('error is : ', err)
    })
  },[])

  useDidMountEffect(()=>{
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
  const CustomTag = ({ classNames, tag, ...tagProps }) => {
    console.log(tag.value);
    return (
      <button type="button" className={classNames.tag} {...tagProps} style={{background : colorArray[tag.value - 1]}}>
        <span className={classNames.tagName}>{tag.label}</span>
      </button>
    )
  }

  return(
    <div className="petIngredientSelect">
      <ReactTags
        suggestions={suggestions}
        placeholderText={selected.length ? "" : "이건 먹으면 안 돼요"}
        selected={selected}
        onAdd={onAdd}
        onDelete={onDelete}
        noOptionsText="일치하는 재료가 없습니다."
        allowBackspace={true}
        renderTag={CustomTag}
      />
    </div>
  );
};

export default PetIngredientTagBar;