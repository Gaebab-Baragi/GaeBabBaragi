import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import './PasswordModal.css'
function PassswordModal(props) {
  return (
    <Modal
      {...props}
      size="sm"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          비밀번호를 입력해주세요.
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <input className='streamingPwInput' type="password"/>
        <div className='pwInfoContainer'>
          <ion-icon size='small' name="alert-circle-outline"></ion-icon>
          <span className='pwInfo'>비밀번호는 6자리 입니다.</span>
        </div>
        <div className='enterContainer'>
          <Button variant='warning' className='pwBtn' onClick={props.onHide}>입력</Button>
        </div>
      </Modal.Body>
    </Modal>
  );
}

export default PassswordModal;

