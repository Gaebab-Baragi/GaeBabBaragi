import { useState } from "react";
import CurrentPasswordCheckForm from "../components/form/CurrentPasswordCheckForm";
import SetNewPasswordForm from "../components/form/SetNewPasswordForm";
import MyPageNavBar from "../components/ui/MyPage/MyPageNavbar";
import MyPageSubNavBar from "../components/ui/MyPage/MyPageSubNavbar";


const PasswordModificationPage = () => {
    const [ currentPassword, setCurrentPassword] = useState('')
    const [ isChecked, setIsChecked ] = useState(false)
    
    return (
        <div className="pageContainer">
        <MyPageNavBar sel={1}></MyPageNavBar>
        <MyPageSubNavBar sel = {2}></MyPageSubNavBar>
        {
            isChecked ? (<SetNewPasswordForm password={currentPassword}/>)
            : (<CurrentPasswordCheckForm setCurrentPassword={setCurrentPassword} setIsChecked={setIsChecked}/>)
        }
        </div>
    )
}

export default PasswordModificationPage;