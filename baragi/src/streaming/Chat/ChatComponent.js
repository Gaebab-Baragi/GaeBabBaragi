import React, { Component } from 'react';
import './ChatComponent.css'
import { useState } from 'react';
export default class ChatComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
        messageList: [],
        message: '',
        chatStatus: true,
        };
    this.chatScroll = React.createRef();

    this.handleChange = this.handleChange.bind(this);
    this.handlePressKey = this.handlePressKey.bind(this);
    this.close = this.close.bind(this);
    this.sendMessage = this.sendMessage.bind(this);
    this.handleChatStatus = this.handleChatStatus.bind(this);
    }

    componentDidMount() {
        // Delay the execution of the component initialization
        setTimeout(() => {
            this.initializeChat();
        },2000); // Adjust the delay time as needed
    }

    initializeChat() {
      console.log('채팅의 getStreamManager()임!!!!!',this.props.user.getStreamManager())
        this.props.user.getStreamManager().stream.session.on('signal:chat', (event) => {
            const data = JSON.parse(event.data);
            let messageList = this.state.messageList;
            messageList.push({ connectionId: event.from.connectionId, nickname: data.nickname, message: data.message });
            const document = window.document;
            this.setState({ messageList: messageList });
            this.scrollToBottom();
        });
    }

    handleChange(event) {
        this.setState({ message: event.target.value });
    }

    handlePressKey(event) {
        if (event.key === 'Enter') {
            this.sendMessage();
        }
    }

    handleChatStatus() {
      this.setState({chatStatus: !this.state.chatStatus})
    }

    sendMessage() {
    console.log(this.state.message);
    if (this.props.user && this.state.message) {
      let message = this.state.message.replace(/ +(?= )/g, '');
      if (message !== '' && message !== ' ') {
          const data = { message: message, nickname: this.props.user.getNickname(), streamId: this.props.user.getStreamManager().stream.streamId };
          this.props.user.getStreamManager().stream.session.signal({
              data: JSON.stringify(data),
              type: 'chat',
          });
    }
    }
    this.setState({ message: '' });
    }

    scrollToBottom() {
    setTimeout(() => {
        try {
            this.chatScroll.current.scrollTop = this.chatScroll.current.scrollHeight;
        } catch (err) {}
    }, 20);
    }

  close() {
    this.props.close(undefined);
  }

  render() {

    return (
      <div className='totalChatContainer'>
          {/* 제목 */}
          <div className="titleContainer">
            <p className='chatTitleDetail'> <span onClick={this.handleChatStatus}>레시피 보기</span> <span>|</span> <span onClick={this.handleChatStatus}>채팅</span> </p>
          </div>
      {this.state.chatStatus 
      ?

        <div id="chatContainer">
          {/* 채팅 목록 */}
          <div id="chatComponent">
            <div className="message-wrap" ref={this.chatScroll}>
                {this.state.messageList.map((data, i) => (
                  <div
                    key={i}
                    id="remoteUsers"
                    className={
                        'message' + (data.connectionId !== this.props.user.getConnectionId() ? ' left' : ' right')
                    }
                  >
                      <div className="msg-info">
                          <span>{data.nickname} : </span>
                          <span>{data.message}</span>
                      </div>
                  </div>
                  ))}
            </div>
          </div>
          {/* 제출하기 input */}
          <div id="messageInput">
            <input
              className='messageSendInput'
              placeholder=""
              id="chatInput"
              value={this.state.message}
              onChange={this.handleChange}
              onKeyPress={this.handlePressKey}
            />
            <ion-icon class="messageSendButton" name="arrow-forward-circle-outline" onClick={this.sendMessage}></ion-icon>
          </div>
        </div>
      :
      <div className='recipeContainer'>
        <div className='recipe-info'>
          <div className='recipe-title'>재료</div>
          {this.props.recipeData.recipeIngredients.map((ing)=>{
            return <div>{ing.ingredientsName} {ing.amount}</div>
          })}
        </div>

        <div className='recipe-info'>
          <div className='recipe-title'>레시피</div>
          {this.props.recipeData.steps.map((step)=>{
            return(
              <div>{step.orderingNumber} {step.descriptions}</div>
            )
          })}
        </div>
      </div>
      }
      </div>
    );
  }
}