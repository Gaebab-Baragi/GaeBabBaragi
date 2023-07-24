import React, { useState } from 'react';

function InputMat() {
    const [Matname, setMatname] = useState('');
    const handleMatnameChange = (e) => {
        setMatname(e.target.value);
      };
    const [Matamount, setMatamount] = useState('');
    const handleMatamountChange = (e) => {
        setMatamount(e.target.value);
    };
  return (
    <>
    <div style={{ backgroundColor :'#FFEACB', display: 'flex', alignItems: 'center', justifyContent: 'space-between' , padding: '10px 0' }}>
        <div style={{ margin: '5px 10px' }}>
        <input
          type="text"
          id="Matname"
          name="Matname"
          value={Matname}
          onChange={handleMatnameChange}
          placeholder="재료를 입력하세요"
        />
        </div>
        <div style={{ margin: '5px 10px' }}>
        <input
          type="text"
          id="Matamount"
          name="Matamount"
          value={Matamount}
          onChange={handleMatamountChange}
          placeholder="양을 입력하세요"
        />
        </div>
    </div>
    </>
  );
}

export default InputMat;