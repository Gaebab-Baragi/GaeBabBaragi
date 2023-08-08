import React, { Component } from 'react';
import './ChatComponent.css'

export default class ChatComponent extends Component {
    constructor(props) {
        super(props);
        this.state = {
        messageList: [],
        message: '',
    };
    this.chatScroll = React.createRef();

    this.handleChange = this.handleChange.bind(this);
    this.handlePressKey = this.handlePressKey.bind(this);
    this.close = this.close.bind(this);
    this.sendMessage = this.sendMessage.bind(this);
    }

    componentDidMount() {
        // Delay the execution of the component initialization
        setTimeout(() => {
            this.initializeChat();
        },1000); // Adjust the delay time as needed
    }

    initializeChat() {
      console.log(this.props.user.getStreamManager())
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
            <p className='chatTitleDetail'> 레시피 보기 | 채팅</p>
          </div>

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
      </div>
    );
  }
}