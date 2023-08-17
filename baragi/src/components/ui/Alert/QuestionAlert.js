import Swal from "sweetalert2";

const QuestionAlert=()=>{
  return Swal.fire({
    title: '질문이 있어요!', 
    icon:'question',     
    showClass: {
      popup: 'animate__animated animate__fadeInDown'
    },
    hideClass: {
      popup: 'animate__animated animate__fadeOutUp'
    },
    showConfirmButton: false,
    timer: 2500,
    });                   
  };

  export default QuestionAlert;