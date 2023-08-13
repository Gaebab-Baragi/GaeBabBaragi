import CurrentPasswordCheckForm from "../components/form/CurrentPasswordCheckForm";
import MemberModificationForm from "../components/form/MemberModificationForm";
import MyPageNavBar from "../components/ui/MyPage/MyPageNavbar";
import MyPageSubNavBar from "../components/ui/MyPage/MyPageSubNavbar";

function MyinformationPage() {
    return (
        <> 
            <MyPageNavBar sel={1}></MyPageNavBar>
            <MyPageSubNavBar sel={1}></MyPageSubNavBar>
            <MemberModificationForm></MemberModificationForm>
        </>
      );
    }
export default MyinformationPage;
