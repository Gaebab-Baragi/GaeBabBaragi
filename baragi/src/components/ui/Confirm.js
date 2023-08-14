import Swal from "sweetalert2";

const Confirm = () => {
  return Swal.fire({
    title: '로그인이 필요한 서비스입니다.',
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#3085d6',
    cancelButtonColor: '#d33',
    confirmButtonText: '로그인하러 가기',
    cancelButtonText: '취소',
    reverseButtons: true,
  }).then(result => {
    if (result.isConfirmed) {
      window.location.href = '/login';
    }
  });
};

export default Confirm;