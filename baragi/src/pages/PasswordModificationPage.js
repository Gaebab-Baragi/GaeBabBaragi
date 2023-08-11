import { useState } from "react";
import CurrentPasswordCheckForm from "../components/form/CurrentPasswordCheckForm";
import SetNewPasswordForm from "../components/form/SetNewPasswordForm";
import MyPageNavBar from "../components/ui/MyPage/MyPageNavbar";


const PasswordModificationPage = () => {
    const [ currentPassword, setCurrentPassword] = useState('')
    const [ isChecked, setIsChecked ] = useState(false)
    
    return (
        <>
        <MyPageNavBar></MyPageNavBar>
        {
            isChecked ? (<SetNewPasswordForm password={currentPassword}/>)
            : (<CurrentPasswordCheckForm setCurrentPassword={setCurrentPassword} setIsChecked={setIsChecked}/>)
        }
        </>
    )
}

export default PasswordModificationPage;