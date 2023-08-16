import React from "react";
import { useEffect } from "react";
import './Modal.css'

function Modal(props) {

  useEffect(() => {
    document.body.style.overflow = "hidden";
    // modal 닫히면 다시 스크롤 가능하도록 함
    return () => document.body.style.overflow = "unset";
  }, []);

  function closeModal() {
      props.closeModal();
  }

  return (
    <div className="Modal" onClick={closeModal}>
      <div className="modalBody" onClick={(e) => e.stopPropagation()}>
        <button id="modalCloseBtn" onClick={closeModal}>
          ✖
        </button>
        {props.children}
      </div>
    </div>
  );
}
 
export default Modal;
