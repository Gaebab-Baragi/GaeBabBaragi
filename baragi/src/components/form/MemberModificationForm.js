import { useCallback, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import axios from "axios";
import Image from 'react-bootstrap/Image'
import "./css/MemberModification.css"

function MemberModificationForm(){
    const navigate = useNavigate();
    const user = useSelector((state) => (state.user));
    const [nickname, setNickname] = useState('');
    const [nicknameDuplicateCheck, setNicknameDuplicateCheck] = useState(false);
    const [validNickname, setValidNickname] = useState(false);
    const [profileUrl, setProfileUrl] = useState(user.profileUrl);
    
    useEffect(()=>{
        if (nickname.length == 0) setNickname(user.nickname); 
        setValidNickname(nickname.length<=30)
    },[nickname])

    const handleNicknameDuplicateCheck = useCallback((e) => {
        e.preventDefault();
        console.log('Nickname-Duplication-Check')
        let body = JSON.stringify({
            nickname : nickname,
        })

        axios.post('/member/modify/nickname', body, {
        headers: { "Content-Type": `application/json; charset= UTF-8`}
        })
        .then((res)=>{
        if (res.status === 200) {
            console.log('duplication checked')
            alert("사용 가능한 닉네임입니다.")
            setNicknameDuplicateCheck(true);
        }
        })
        .catch((res) => {
        res = res.response;
        if (res.status === 460) alert("잘못된 닉네임 형식입니다.")
        else if (res.status === 457) alert("이미 사용중인 닉네임입니다.")
        else alert("이유를 알 수 없는 오류");
        })

    }, [nickname]);


    const onSubmit = (e) => {
        e.preventDefault();
        if (!nicknameDuplicateCheck) {
            alert('중복 확인을 진행해주세요.')
        } 
        else {
            let body = {
                nickname : nickname,
            };
            axios.post('/member/modify', body)
            .then((res)=>{
            if (res.status ===200) {
                console.log('signup success')

            }
            })
            .catch((res) => {
            console.log(res);
                alert("회원정보를 수정할 수 없습니다.")
            }
            );
        }
    }

    const onProfileChange = (e) =>{
        e.preventDefault();
        if (!e.target.files || !e.target.files[0]) return;
        const file = e.target.files[0];
        const reader = new FileReader();
        reader.onload = () => {
            setProfileUrl(reader.result);
        }
        reader.readAsDataURL(file);
    }

    return (
        <div className="formContainer">
        <form className="member-info-form form" onSubmit={onSubmit}>
            <div className="formTitle">프로필 수정</div>
            {/* 프로필 이미지 */}

            <label htmlFor="photo-upload" className="custom-file-upload fas">
                <div className="img-wrap img-upload" >
                    <img className="member-profile-img" src={profileUrl} htmlFor="photo-upload"/>
                </div>
                <input id="photo-upload" type="file" onChange={(e) => {onProfileChange(e)}}/> 
            </label>

            <hr/>

            {/* 내 아이디(이메일) */}
            <div className="formGroup">
                <label htmlFor="formNickname" className="member-info-label"> 이메일 </label> 
                <input className='formInput' type="id" id="myEmail" placeholder={user.username} disabled />
            </div>

            {/* 닉네임 수정*/}
            <div className="formGroup">
                <label htmlFor="formNickname" className="member-info-label"> 닉네임 </label> 
                <div className="formGroupComponent">
                    <input className='formInput' onChange={e=>{setNickname(e.target.value); setNicknameDuplicateCheck(false);}} type="text" id='formNickname' placeholder={user.nickname} />
                    <button className='duplicationCheckButton' onClick={handleNicknameDuplicateCheck}>중복 확인</button>
                </div>
                { !validNickname && nickname.length>=1 && (
                    <div className="errorMsg">닉네임은 30자 이내로 작성해주세요.</div>
                )}
            </div>

            <button className="submitButton" type="submit">
            프로필 수정 
            </button>

        </form>
        </div>
    );
}

export default MemberModificationForm;