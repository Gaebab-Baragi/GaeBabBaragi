import { useCallback, useState, useEffect } from "react";
import { useDispatch } from 'react-redux';
import "./css/MemberModification.css"
import axios from "axios";

function MemberModificationForm(){
    const [username, setUsername] = useState('');
    const [profileUrl, setProfileUrl] = useState('');
    const [nickname, setNickname] = useState('');
    const [originNickname, setOriginNickname] = useState('');
    const [nicknameDuplicateCheck, setNicknameDuplicateCheck] = useState(false);
    const [validNickname, setValidNickname] = useState(false);
    const [base64, setBase64] = useState('');
    const [fileType, setFileType] = useState('');

    useEffect(()=> {
        axios.get("/api/member")
        .then((res) => {
            if (res.status === 200){
                setOriginNickname(res.data.nickname);
                setUsername(res.data.username);
                setProfileUrl(res.data.profile_url);
            }
            else {
                alert("회원 정보를 불러 올 수 없습니다.");
            }
        })
        .catch((res) => {
            alert("회원 정보를 불러 올 수 없습니다.");
        })
    }, [])

    useEffect(()=>{
        setValidNickname(nickname.length<=30)
        if (nickname.length === 0) {
            setNicknameDuplicateCheck(true);
        }
        else setNicknameDuplicateCheck(false);
    },[nickname])

    const handleNicknameDuplicateCheck = useCallback((e) => {
        e.preventDefault();
        console.log('Nickname-Duplication-Check')

        let body = JSON.stringify({
            nickname : nickname ? nickname : originNickname,
        })

        axios.post('/api/member/modify/nickname', body, {
            headers: { 
                "Content-Type": `application/json; charset= UTF-8`
            }
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

    }, [nickname, originNickname]);


    const onSubmit = (e) => {
        e.preventDefault();
        if (!nicknameDuplicateCheck) {
            alert('중복 확인을 진행해주세요.')
        } 
        else {
            let body = JSON.stringify({
                username : username,
                nickname : nickname ? nickname : originNickname,
                file : base64,
                fileType : fileType
            });
            axios.put('/api/member/modify', body)
            .then((res)=>{
                    let data = res.data;
                    alert("회원 정보를 수정했습니다.");
            })
            .catch((res) => {
                console.log(res);
                alert("회원정보를 수정할 수 없습니다.")
            });
        }
    }

    const onProfileChange = (e) =>{
        e.preventDefault();
        if (!e.target.files || !e.target.files[0]) return;
        const file = e.target.files[0];
        setFileType(file.type);
        console.log(file.name);
        console.log(file.type);
        const reader = new FileReader();
        reader.onload = () => {
            setProfileUrl(reader.result);
            const dataIndex = reader.result.indexOf(',') + 1
            setBase64(reader.result.substring(
                            dataIndex,
                            reader.result.length));
        }
        reader.readAsDataURL(file);
    }

    const letsTest = (e) => {
        e.preventDefault();
        axios.get("/api/member/test")
        .then((res) =>{
        if (res.status === 200){
            console.log("yes!");
        }
        console.log(res);
        })
        .catch((res) => {
            console.log(axios.defaults.headers['Authorization']);
            console.log(axios.defaults.baseURL);
            console.log(res);
        })
    }


    return (
        <div className="formContainer">
        <form className="member-info-form form" onSubmit={onSubmit}>
            <div className="formTitle">프로필 수정</div>
            {/* 프로필 이미지 */}

            <label htmlFor="photo-upload" className="custom-file-upload fas">
                <div className="img-wrap img-upload" >
                    <img className="member-profile-img" src={profileUrl} htmlFor="photo-upload" alt="프로필"/>
                </div>
                <input id="photo-upload" type="file" onChange={(e) => {onProfileChange(e)}}/> 
            </label>

            <hr/>

            {/* 내 아이디(이메일) */}
            <div className="formGroup">
                <label htmlFor="formNickname" className="member-info-label"> 이메일 </label> 
                <input className='formInput' type="id" id="myEmail" placeholder={username} disabled />
            </div>

            {/* 닉네임 수정*/}
            <div className="formGroup">
                <label htmlFor="formNickname" className="member-info-label"> 닉네임 </label> 
                <div className="formGroupComponent">
                    <input className='formInput' onChange={e=>{setNickname(e.target.value)}} type="text" id='formNickname' placeholder={originNickname} />
                    <button className='duplicationCheckButton' onClick={handleNicknameDuplicateCheck}>중복 확인</button>
                </div>
                { !validNickname && nickname.length>=1 && (
                    <div className="errorMsg">닉네임은 30자 이내로 작성해주세요.</div>
                )}
            </div>

            <button className="submitButton" type="submit">
            프로필 수정 
            </button>

            <button type="button" onClick={letsTest}>테스트입니다</button>
        </form>
        </div>
    );
}

export default MemberModificationForm;