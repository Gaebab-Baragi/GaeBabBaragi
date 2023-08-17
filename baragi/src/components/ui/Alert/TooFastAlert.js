import Swal from "sweetalert2";

const TooFastAlert=()=>{
  return Swal.fire({
    title: '너무 빨라요!', 
    icon:'warning',     
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

  export default TooFastAlert;