import Swal from "sweetalert2";

const CommentAlert=()=>{
    return Swal.fire({
        title: '내용을 입력해주세요.',       // Alert 제목
        text: '댓글 내용을 입력해주세요.',  // Alert 내용
        icon:'warning', 
        confirmButtonText :'확인'
    });                       // Alert 타입
  };

  export default CommentAlert;