import { useEffect, useState } from 'react'
import React from 'react';
//import AudioPlayer from 'react-h5-audio-player';
//import 'react-h5-audio-player/lib/styles.css';
import PlayerApp from './AudioPlayer';
function UserDashboard() {


// const http = require('http');
    //  const[stateVariableName, setStateVariableFunction] = useState(stateVariableValue) 
    var userIsLoggedIn = window.sessionStorage.getItem("userIsLoggedIn");
    var email = window.sessionStorage.getItem("email");
    
    // import 'react-h5-audio-player/lib/styles.less' Use LESS
// import 'react-h5-audio-player/src/styles.scss' Use SASS

// const Player = () => (
//   <AudioPlayer
//     autoPlay
//     src=""
//     onPlay={e => console.log("onPlay")}
//     // other props here
//   />
// );


    return (

        <>
        <div className='container'>
            Dashboard Component
        </div>
        <div className="container">
    
      
        <div>
            <PlayerApp></PlayerApp>
        </div>
      </div>
    </>

    );

}

export default UserDashboard;