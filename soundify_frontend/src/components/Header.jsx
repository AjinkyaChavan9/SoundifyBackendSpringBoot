import React, { Component } from 'react';
import logo from '../assets/logo/png/logo-color-edit.png'
class Header extends Component {
    
    render() { 
        return (
            <center >
                <h1><img src={logo}
                alt="Logo"
                style={{ width: 'auto', height: '95px', marginRight: '10px' }}></img></h1>
            </center>
        );
    }
}
 
export default Header;