import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../../../node_modules/bootstrap/dist/css/bootstrap.min.css';

export const SignUp = () => {
  const [credentials, setCredentials] = useState({
    name: '',
    firstName: '',
    lastName: '',
    email: '',
    dateOfBirth: '',
    password: '',
    confirmPassword: '',

  });
  const [message, setMessage] = useState('');

  const navigate = useNavigate();

  var OnTextChange = (args) => {
    var copyOfCredentials = { ...credentials }
    copyOfCredentials[args.target.name] = args.target.value;
    setCredentials(copyOfCredentials);

  }

  var ShowMessage = (message) => {
    setMessage(message);
    setTimeout(() => { setMessage("") }, 3000)

  }

  const signUpDB = () => {
    if (credentials.password !== credentials.confirmPassword) {
      ShowMessage('Passwords do not match');
      return;
    }

    axios
      .post('http://127.0.0.1:8080/api/artists/signup', credentials, {
        headers: { 'content-type': 'application/json' },
      })
      .then((response) => {
        const result = response.data;

        if (result.status == 'success') {
          ShowMessage('Signup successful');
          setTimeout(() => {
            navigate('/artistlogin');
          }, 3000);
        } else {
          ShowMessage('Error, please register again');
        }
      })
      .catch((error) => {
        console.error('An error occurred:', error);
        ShowMessage('An error occurred while signing up');
      });
  };




  return (
    <div className="container">
      <form className="">
        <div className="form-group">
          {/* <h1></h1> */}
        </div>

        <div className="row">
          <div className="input-field col s6">
            <input
              id="name"
              name="name"
              type="text"
              className="validate"
              value={credentials.name}
              onChange={OnTextChange}
            />
            <label htmlFor="name">Artist Name</label>
          </div>
        </div>

        <div className="row">
          <div className="input-field col s6">
            <input
              id="firstName"
              name="firstName"
              type="text"
              className="validate"
              value={credentials.firstName}
              onChange={OnTextChange}
            />
            <label htmlFor="firstName">First Name</label>
          </div>
          <div className="input-field col s6">
            <input
              id="lastName"
              name="lastName"
              type="text"
              className="validate"
              value={credentials.lastName}
              onChange={OnTextChange}
            />
            <label htmlFor="lastName">Last Name</label>
          </div>
        </div>

        <div className="row">
          <div className="input-field col s6">
            <input
              id="email"
              name="email"
              type="email"
              className="validate"
              value={credentials.email}
              onChange={OnTextChange}
            />
            <label htmlFor="email">Email</label>
          </div>
          <div className="input-field col s6">
            <input
              id="dateOfBirth"
              name="dateOfBirth"
              type="date"
              className="validate"
              value={credentials.dateOfBirth}
              onChange={OnTextChange}
            />
            <label htmlFor="dateOfBirth">Date Of Birth</label>
          </div>
        </div>

        <div className="row">
          <div className="input-field col s6">
            <input
              id="password"
              name="password"
              type="password"
              className="validate"
              value={credentials.password}
              onChange={OnTextChange}
            />
            <label htmlFor="password">Password</label>
          </div>
          <div className="input-field col s6">
            <input
              id="confirmPassword"
              name="confirmPassword"
              type="password"
              className="validate"
              value={credentials.confirmPassword}
              onChange={OnTextChange}
            />
            <label htmlFor="confirmPassword">Confirm Password</label>
          </div>
        </div>



        <div className="text-center">
          <button
            type="button"
            className="btn btn-primary"
            onClick={signUpDB}
          >
            Register
          </button>
        </div>
        {message && (
          <div className="card-panel teal lighten-2">{message}</div>
        )}
      </form>
    </div>
  );
};

