import React, { Component } from 'react';
import OpenViduVideoComponent from './OvVideo';
import './UserVideo.css';

export default class UserVideoComponent extends Component {

    getNicknameTag() {
        // Gets the nickName of the user
        return JSON.parse(this.props.streamManager.stream.connection.data).clientData;
    }
    render() {
        return (
            <div>
                {console.log('1',this.props.size)}
                {this.props.streamManager !== undefined ? (
                    <div className={"streamcomponent-"+this.props.size}>
                        <OpenViduVideoComponent size={this.props.size} streamManager={this.props.streamManager} />
                        <div>{this.getNicknameTag()}</div>
                    </div>
                ) : null}
            </div>
        );
    }
}