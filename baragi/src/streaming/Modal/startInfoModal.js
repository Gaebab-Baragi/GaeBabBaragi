import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import './startInfoModal.css'
import { useState } from 'react';

function StartInfoModal(props) {
  const handleStartEnter = (e)=> {
    e.preventDefault()
    props.onHide();
    props.handlerequestStartSession();
  }

  return (
    <Modal
      {...props}
      size="md"
      aria-labelledby="contained-modal-title-vcenter"
      centered
    >
      <Modal.Header closeButton>
        <Modal.Title id="contained-modal-title-vcenter">
          안내
        </Modal.Title>
      </Modal.Header>
      <Modal.Body>
          <p>미팅 시작 후에는 더 이상 입장이 불가능합니다.</p>
          <p>미팅을 시작하려면 '시작하기' 버튼을 눌러주세요.</p>
        <div className='enterContainer'>
          <Button variant='warning' className='pwBtn' onClick={handleStartEnter}>시작하기</Button>
        </div>
      </Modal.Body>
    </Modal>
  );
}

export default StartInfoModal;