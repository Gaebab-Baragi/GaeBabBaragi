/* eslint-disable */
import StreamingForm from "../components/form/StreamingForm";



function StreamingPage() {
    return (

        <div style={{ marginLeft:'10%' , marginRight : '10%' , marginTop : '0.5%' , marginBottom : '10%'}}>
          <h2 style={{ textAlign : 'left' }}>스트리밍 예약하기</h2>
          <h4 style={{ textAlign : 'left', marginLeft:'2%' }}>스트리밍 기본 정보 입력</h4>
        <StreamingForm></StreamingForm>
        </div>

      );
    }
    export default StreamingPage;