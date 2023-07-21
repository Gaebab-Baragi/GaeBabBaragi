import React from 'react';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import './Form.css';

function LoginForm() {
  return (
    <div className="d-flex justify-content-center align-items-center h-100">
      <Form className='LoginForm'>
        <Form.Group className="mb-4">
          <span className='formTitle'>LOGIN</span>
        </Form.Group>

        {/* 아이디 입력  */}
        <Form.Group className="input mb-3" controlId="formBasicEmail">
          <Form.Control  type="email" placeholder="아이디를 입력해주세요." />
        </Form.Group>

        {/* 비밀번호 입력 */}
        <Form.Group className="input mb-3" controlId="formBasicPassword">
          <Form.Control type="password" placeholder="비밀번호를 입력해주세요." />
        </Form.Group>

        {/* (아이디, 비번) 찾기  */}

        {/* Login 버튼 - outline 제거 */}
        <Button className='btn btn-custom-class' variant='outline-' type="submit">
          LOGIN
        </Button>
      </Form>
    </div>
  );
}

export default LoginForm;
