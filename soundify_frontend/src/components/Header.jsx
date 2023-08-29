import React, { Component } from 'react';
import logo from '../assets/logo/png/logo-color-edit.png'
import { Link } from 'react-router-dom';
class Header extends Component {
    
    render() { 
        return (
            <center >
                <h1><a href="/"><img src={logo}
                alt="Logo"
                style={{ width: 'auto', height: '95px', marginRight: '10px' }}></img></a></h1>
            </center>
        );
    }
}
 
export default Header;