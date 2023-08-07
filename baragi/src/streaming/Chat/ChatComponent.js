import React, { useState } from "react";
import './ChatComponent.css'
import CardPaginationList from './../../components/list/CardPagination';

function ChatComponent() {
  const [chatList, setChatList] = useState([{ content: '재밌겠다~~', member_id: 1 }]);
  const [inputValue, setInputValue] = useState('');

  const handleInputChange = (event) => {
    setInputValue(event.target.value);
  };

  const handleKeyPress = (event) => {
    if (event.key === 'Enter') {
      event.preventDefault(); 
      if (inputValue.trim() !== '') {
        const newChat = { content: inputValue, member_id: 1 };
        setChatList((prevChatList) => [...prevChatList, newChat]);
        setInputValue('');
      }
    }
  };
  
  return (
    <div className="ChatContainer">
      <p className="chatTitle">채팅</p>
      <div className="ChatMessages">
        {chatList.map((chat, index) => (
          <div className="messageContainer"  key={index}>
            <span>user: </span>
            <span className="chatContent">{chat.content}</span>
          </div>
        ))}
      </div>
      <input
        className="chatInput"
        type="text"
        placeholder="채팅을 보내보세요"
        value={inputValue}
        onChange={handleInputChange}
        onKeyPress={handleKeyPress}
      />
    </div>
  );
}

export default ChatComponent;
