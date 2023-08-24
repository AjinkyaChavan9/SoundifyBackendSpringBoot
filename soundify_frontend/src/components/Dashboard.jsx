import { useEffect, useState } from 'react'
import React from 'react';

import '../../node_modules/bootstrap/dist/css/bootstrap.min.css'

// const http = require('http');

function Dashboard() {

    //  const[stateVariableName, setStateVariableFunction] = useState(stateVariableValue) 
    var userIsLoggedIn = window.sessionStorage.getItem("userIsLoggedIn");
    var email = window.sessionStorage.getItem("email");


    return (


        <div className='container'>
            Dashboard Component
        </div>
    )



}

export default Dashboard;