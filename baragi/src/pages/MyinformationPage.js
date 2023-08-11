import CurrentPasswordCheckForm from "../components/form/CurrentPasswordCheckForm";
import MemberModificationForm from "../components/form/MemberModificationForm";
import MyPageNavBar from "../components/ui/MyPage/MyPageNavbar";

function MyinformationPage() {
    return (
        <div> 
            <MyPageNavBar></MyPageNavBar>
            <MemberModificationForm></MemberModificationForm>
        </div>
      );
    }
export default MyinformationPage;
