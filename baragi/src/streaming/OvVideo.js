import React, { Component } from 'react';
import  './OvVideo.css'

export default class OpenViduVideoComponent extends Component {

    constructor(props) {
        super(props);
        this.videoRef = React.createRef();
    }

    componentDidUpdate(props) {
        if (props && !!this.videoRef) {
            this.props.streamManager.addVideoElement(this.videoRef.current);
        }
    }

    componentDidMount() {
        if (this.props && !!this.videoRef) {
            this.props.streamManager.addVideoElement(this.videoRef.current);
        }
    }



    render() {
        // {console.log('2',this.props.size)}
        return <video className={this.props.size} autoPlay={true} ref={this.videoRef} />;
    }

}
