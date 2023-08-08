import { OpenVidu } from 'openvidu-browser';
import axios from 'axios';
import React, { Component, useEffect } from 'react';
import './Streaming.css';
import UserVideoComponent from './UserVideoComponent';
import UserModel from './user-model';
import ChatComponent from './Chat/ChatComponent';
var localUser = new UserModel();

const APPLICATION_SERVER_URL = process.env.NODE_ENV === 'production' ? '' : 'http://localhost:8083/api/';
// const APPLICATION_SERVER_URL = process.env.NODE_ENV === 'production' ? '' : 'http://localhost:3000/';

class Streaming extends Component {
    constructor(props) {
        super(props);

        this.hasJoinedSession = false;

        this.state = {
            mySessionId: props.sessionId.toString(),
            myUserName: props.nickname,
            host: props.host_nickname,
            session: undefined,
            mainStreamManager: undefined,  // Main video of the page. Will be the 'publisher' or one of the 'subscribers'
            publisher: undefined,
            subscribers: [],
            chatDisplay: true,
        };

        this.joinSession = this.joinSession.bind(this);
        this.leaveSession = this.leaveSession.bind(this);
        // this.toggleChat = this.toggleChat.bind(this);
    
        this.handleMainVideoStream = this.handleMainVideoStream.bind(this);
        this.onbeforeunload = this.onbeforeunload.bind(this);
    }

    componentDidMount() {
        window.addEventListener('beforeunload', this.onbeforeunload);
        if (!this.hasJoinedSession) {
            this.joinSession();
            this.hasJoinedSession = true; // Mark joinSession as called
        }
    }

    componentWillUnmount() {
        window.removeEventListener('beforeunload', this.onbeforeunload);
        this.leaveSession()
    }

    onbeforeunload(event) {
        this.leaveSession();
    }


    handleMainVideoStream(stream) {
        if (this.state.mainStreamManager !== stream) {
            this.setState({
                mainStreamManager: stream
            });
        }
    }

    deleteSubscriber(streamManager) {
        let subscribers = this.state.subscribers;
        let index = subscribers.indexOf(streamManager, 0);
        if (index > -1) {
            subscribers.splice(index, 1);
            this.setState({
                subscribers: subscribers,
            });
        }
    }

    joinSession() {
        // --- 1) Get an OpenVidu object ---

        this.OV = new OpenVidu();

        // --- 2) Init a session ---

        this.setState(
            {
                session: this.OV.initSession(),
            },
            () => {
                var mySession = this.state.session;

                // --- 3) Specify the actions when events take place in the session ---

                // On every new Stream received...
                mySession.on('streamCreated', (event) => {
                    // Subscribe to the Stream to receive it. Second parameter is undefined
                    // so OpenVidu doesn't create an HTML video by its own
                    var subscriber = mySession.subscribe(event.stream, undefined);
                    var subscribers = this.state.subscribers;
                    subscribers.push(subscriber);

                    // Update the state with the new subscribers
                    this.setState({
                        subscribers: subscribers,
                    });
                });

                // On every Stream destroyed...
                mySession.on('streamDestroyed', (event) => {

                    // Remove the stream from 'subscribers' array
                    this.deleteSubscriber(event.stream.streamManager);
                });

                // On every asynchronous exception...
                mySession.on('exception', (exception) => {
                    console.warn(exception);
                });

                // --- 4) Connect to the session with a valid user token ---

                // Get a token from the OpenVidu deployment

                this.getToken().then((token) => {
                    // First param is the token got from the OpenVidu deployment. Second param can be retrieved by every user on event
                    // 'streamCreated' (property Stream.connection.data), and will be appended to DOM as the user's nickname
                    mySession.connect(token, { clientData: this.state.myUserName })
                        .then(async () => {

                            // --- 5) Get your own camera stream ---


                            // Init a publisher passing undefined as targetElement (we don't want OpenVidu to insert a video
                            // element: we will manage it on our own) and with the desired properties
                            let publisher = await this.OV.initPublisherAsync(undefined, {
                                audioSource: undefined, // The source of audio. If undefined default microphone
                                videoSource: undefined, // The source of video. If undefined default webcam
                                publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
                                publishVideo: true, // Whether you want to start publishing with your video enabled or not
                                resolution: '640x480', // The resolution of your video
                                frameRate: 30, // The frame rate of your video
                                insertMode: 'APPEND', // How the video is inserted in the target element 'video-container'
                                mirror: false, // Whether to mirror your local video or not
                            });

                            // --- 6) Publish your stream ---

                            mySession.publish(publisher);

                            // Obtain the current video device in use
                            var devices = await this.OV.getDevices();
                            var videoDevices = devices.filter(device => device.kind === 'videoinput');
                            var currentVideoDeviceId = publisher.stream.getMediaStream().getVideoTracks()[0].getSettings().deviceId;
                            var currentVideoDevice = videoDevices.find(device => device.deviceId === currentVideoDeviceId);

                            // Set the main video in the page to display our webcam and store our Publisher
                            this.setState({
                                currentVideoDevice: currentVideoDevice,
                                mainStreamManager: publisher,
                                publisher: publisher,
                            });
                            localUser.setNickname(this.state.myUserName);
                            localUser.setConnectionId(this.state.session.connection.connectionId);
                            localUser.setStreamManager(publisher);
                        })
                        .catch((error) => {
                            console.log('There was an error connecting to the session:', error.code, error.message);
                        });
                });
            },
        );
    }

    updateSubscribers() {
      var subscribers = this.remotes;
      this.setState(
          {
              subscribers: subscribers,
          },
          () => {
              if (this.state.localUser) {
                  this.sendSignalUserChanged({
                      isAudioActive: this.state.localUser.isAudioActive(),
                      isVideoActive: this.state.localUser.isVideoActive(),
                      nickname: this.state.localUser.getNickname(),
                      isScreenShareActive: this.state.localUser.isScreenShareActive(),
                  });
              }
              this.updateLayout();
          },
      );
  }

    leaveSession() {
      window.location.replace('/streaming-list')
      const sessionId = parseInt(this.state.mySessionId)
        console.log(sessionId, typeof(sessionId))
        axios.post(`http://localhost:8083/api/meetings/left/${sessionId}`)
            .then(() => {
                // Leave the session and perform navigation after the axios call
                const mySession = this.state.session;

                if (mySession) {
                    mySession.disconnect();
                }

                this.OV = null;
                this.setState({
                    session: undefined,
                    subscribers: [],
                    mySessionId: 'SessionA',
                    myUserName: 'Participant' + Math.floor(Math.random() * 100),
                    mainStreamManager: undefined,
                    publisher: undefined
                });

                window.location.replace('/streaming-list')
            })
            .catch(error => {
                console.error('Error leaving session:', error);
            });

        
    }

    camStatusChanged() {
      localUser.setVideoActive(!localUser.isVideoActive());
      localUser.getStreamManager().publishVideo(localUser.isVideoActive());
      this.sendSignalUserChanged({ isVideoActive: localUser.isVideoActive() });
      this.setState({ localUser: localUser });
  }

  micStatusChanged() {
      localUser.setAudioActive(!localUser.isAudioActive());
      localUser.getStreamManager().publishAudio(localUser.isAudioActive());
      this.sendSignalUserChanged({ isAudioActive: localUser.isAudioActive() });
      this.setState({ localUser: localUser });
  }
    
    render() {
        const mySessionId = this.state.mySessionId;
        const myUserName = this.state.myUserName;
        const publisher = this.state.publisher;
        const streamingInfo = this.props.streamingInfo;

        return (
            <div className='StreamingLiveContatiner'>

                <div className="streamingContainer">
                    <div className='streamingTop'>
                        <h3 style={{fontWeight:'bold'}}>{streamingInfo.title}</h3>
                        <p>시작 시간 : {streamingInfo.start_time}</p>
                    </div>

                    <div>이건 내 화면이고!!!!!!!!</div>

                    <div className='mainVideo'>
                        <div className="stream-container " >
                            <UserVideoComponent
                                streamManager={this.state.publisher} />
                                <p>{this.state.nickname}</p>
                        </div>
                    </div>

                    <p>이건 남의 화면이야!!!!!!!!!!!</p>
                    <div className='subVideos'>
                    {this.state.subscribers.map((sub, i) => (
                        <div key={sub.id} className="stream-container">
                            <span>{sub.id}</span>
                            <UserVideoComponent streamManager={sub} />
                            <br />
                        </div>
                    ))}
                    </div>
                    
                    <div className='streamingBottom'>
                        {/* 화면 on off */}
                        
                        {this.state.videostate  ? (
                            <div className='onIcon'>
                                <ion-icon 
                                onClick={() => {
                                    this.state.publisher.publishVideo(!this.state.videostate);
                                    this.setState({ videostate: !this.state.videostate });
                                }}
                                name="videocam-outline"
                                ></ion-icon>

                            </div>
                        ) : (
                            <ion-icon 
                            onClick={() => {
                                this.state.publisher.publishVideo(!this.state.videostate);
                                this.setState({ videostate: !this.state.videostate });
                            }}
                            name="videocam-off-outline"></ion-icon>
                        )}

                        <button className='leaveButton' onClick={this.leaveSession}>방 나가기</button>
                        
                        {/* 내 마이크 on off */}
                        {this.state.audiostate ?
                            <ion-icon name="volume-mute-outline" 
                            onClick={() => {
                                this.state.publisher.publishAudio(!this.state.audiostate);
                                this.setState({ audiostate: !this.state.audiostate });
                            }}
                            ></ion-icon>
                        :
                            <ion-icon name="volume-high-outline" 
                            onClick={() => {
                                this.state.publisher.publishAudio(!this.state.audiostate);
                                this.setState({ audiostate: !this.state.audiostate });
                            }}
                            ></ion-icon>
                        }
                    </div>
                </div>
                <ChatComponent user={localUser} className="ChatComponent"/>
            </div>
        );
    }


    /**
     * --------------------------------------------
     * GETTING A TOKEN FROM YOUR APPLICATION SERVER
     * --------------------------------------------
     * The methods below request the creation of a Session and a Token to
     * your application server. This keeps your OpenVidu deployment secure.
     *
     * In this sample code, there is no user control at all. Anybody could
     * access your application server endpoints! In a real production
     * environment, your application server must identify the user to allow
     * access to the endpoints.
     *
     * Visit https://docs.openvidu.io/en/stable/application-server to learn
     * more about the integration of OpenVidu in your application server.
     */
    async getToken() {
        const sessionId = await this.createSession(this.state.mySessionId);
        return await this.createToken(sessionId);

    }

    async createSession(sessionId) {
        const response = await axios.post(APPLICATION_SERVER_URL + 'meetings/sessions',{ customSessionId: sessionId }, {
            headers: { 
            'Content-Type': 'application/json', 
        },
        });
        return response.data; // The sessionId
    }

    async createToken(sessionId) {
        const response = await axios.post(APPLICATION_SERVER_URL + 'meetings/sessions/' + sessionId + '/connections', {}, {
            headers: { 
            'Content-Type': 'application/json', 
            },
        });
        return response.data; // The token
    }
}

export default Streaming;