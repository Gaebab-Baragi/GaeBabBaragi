import MemberModificationForm from "../components/form/MemberModificationForm";
import MyPageNavBar from "../components/ui/MyPage/MyPageNavbar";
import MyPageSubNavBar from "../components/ui/MyPage/MyPageSubNavbar";
import './css/PageDefaults.css'

function MyinformationPage() {
    return (
        <div className="pageContainer"> 
            <MyPageNavBar sel={1}></MyPageNavBar>
            <MyPageSubNavBar sel={1}></MyPageSubNavBar>
            <MemberModificationForm></MemberModificationForm>
        </div>
      );
    }
export default MyinformationPage;
