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
    <div style={{ width: '120%' , borderRadius: '5px', backgroundColor :'#FFEACB', display: 'flex', alignItems: 'center', justifyContent: 'space-between'}}>
        <div style = {{margin :'5% 0% 5% 5%'}}>
        <input
          type="text"
          id="Matname"
          name="Matname"
          value={Matname}
          onChange={handleMatnameChange}
          placeholder="재료를 입력하세요"
          style={{textAlign : 'center' }}
        />
        </div>
        <div style = {{margin :'5% 5% 5% 5%'}}>
        <input
          type="text"
          id="Matamount"
          name="Matamount"
          value={Matamount}
          onChange={handleMatamountChange}
          placeholder="양을 입력하세요"
          style={{textAlign : 'center'}}
        />
        </div>
    </div>
    </>
  );
}

export default InputMat;