import { useState } from "react";
import CurrentPasswordCheckForm from "../components/form/CurrentPasswordCheckForm";
import SetNewPasswordForm from "../components/form/SetNewPasswordForm";


const PasswordModificationPage = () => {
    const [ currentPassword, setCurrentPassword] = useState('')
    const [ isChecked, setIsChecked ] = useState(false)
    
    return (
        <>
        {
            isChecked ? (<SetNewPasswordForm password={currentPassword}/>)
            : (<CurrentPasswordCheckForm setCurrentPassword={setCurrentPassword} setIsChecked={setIsChecked}/>)
        }
        </>
    )
}

export default PasswordModificationPage;