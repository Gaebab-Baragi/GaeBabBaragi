import Swal from "sweetalert2";

const TooHardAlert=()=>{
  return Swal.fire({
    title: '너무 어려워요!', 
    icon:'error',     
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

  export default TooHardAlert;