import React, { useState } from "react";
import './ChatComponent.css'

function ChatComponent() {
  const [chatList, setChatList] = useState([{content:'재밌겠다~~',member_id:1}]);
  
  return(
    <div className="ChatContainer">
      {chatList.map((chat)=>{
          <p>{chat[content]}</p>
      })}
    </div>
  )
}

export default ChatComponent;