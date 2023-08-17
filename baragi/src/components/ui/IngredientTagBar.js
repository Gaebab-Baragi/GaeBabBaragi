import React, {useCallback, useEffect, useState} from "react";
import { ReactTags } from "react-tag-autocomplete";
import './IngredientTagBar.css'
import { useDispatch, useSelector } from "react-redux";
import { updateIngredients, requestFilteredRecipeList } from "../../redux/recipeSearchSlice";
import useDidMountEffect from "../../useDidMountEffect";
import axios from "axios";


function IngredientTagBar({}) {
  const [suggestions, setSuggestions] = useState('');
  const [selected, setSelected] = useState([]);
  const [colorArray, setColorArray] = useState([]);
  // const [detectSelected,setdetectSelected] = useState([]);
  const dispatch = useDispatch();
  const detectSelected = useSelector((state)=>state.recipeSearch.detectedingredients)





  useDidMountEffect(()=>{
    
    setSelected([...detectSelected, ...selected]);
    // console.log('합쳐져라 머리머리:', detectSelected)
  }, [detectSelected])

  useDidMountEffect(()=>{
    dispatch(updateIngredients(selected))
    // console.log('궁금증:', selected)
  }, [selected])





  

  // useDidMountEffect(()=>{
  //   dispatch(requestFilteredRecipeList())
  // }, [selected])

  // 재료 리스트 받아와주기
  useEffect(()=>{
    function getRandomBrightColor() {
      const hue = Math.floor(Math.random() * 360);
      const saturation = '100%';
      const lightness = `${Math.floor(Math.random() * 21) + 70}%`;
      return `hsl(${hue}, ${saturation}, ${lightness})`;
    }
  
    axios.get(process.env.REACT_APP_BASE_URL +'/api/ingredients')
    .then((res)=>{
      // console.log('res data : ' ,res.data)
      const tmp = []
      res.data.ingredients.map((i)=>{
        tmp.push({value:i.id, label:i.name})
        setColorArray(colorArray => [...colorArray, getRandomBrightColor()])
      })
      setSuggestions(tmp)
    })
    .catch((err)=>{
      // console.log('error is : ', err)
    })
  },[])

  const onAdd = useCallback(
    (newTag) => {
      setSelected([...selected, newTag])
      //
      // onSelectedChange([...selected, newTag]);
    },
    [selected]
  )

  const onDelete = useCallback(
    (tagIndex) => {
      // setSelected(selected.filter((_, i) => i !== tagIndex))
      const newSelected = selected.filter((_, i) => i !== tagIndex);
      setSelected(newSelected);
      // onSelectedChange(newSelected); // 삭제된 재료 정보를 상위 컴포넌트로 전달
    },
    [selected]  //, onSelectedChange]
  );
    
    // },
      
    // [selected]
    
  // )

  const CustomTag = ({ classNames, tag, ...tagProps }) => {
    // console.log(tag.value);
    return (
      <button type="button" className={classNames.tag} {...tagProps} style={{background : colorArray[tag.value - 1]}}>
        <span className={classNames.tagName}>{tag.label}</span>
      </button>
    )
  }

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
        renderTag={CustomTag}
      />
    </div>
  );
};

export default IngredientTagBar;