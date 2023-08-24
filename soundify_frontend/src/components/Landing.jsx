import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

import ArtistLandingComponent from './Artist/ArtistLanding';
import UserLandingComponent from './User/UserLanding';
import AdminLandingComponent from './Admin/AdminLanding';

function Landing() {
  const navigate = useNavigate();
  const [selectedRole, setSelectedRole] = useState(null);

  // Click handler for the User button
  const handleUserButtonClick = () => {
    setSelectedRole('user'); // Set selectedRole to 'user'
    navigate('/useradmindashboard'); // Navigate to the "/user" route
  };

  // Click handler for the Artist button
  const handleArtistButtonClick = () => {
    setSelectedRole('artist'); // Set selectedRole to 'artist'
    navigate('/artistadmindashboard'); // Navigate to the "/artist" route
  };

  const handleAdminButtonClick = () => {
    setSelectedRole('admin'); // Set selectedRole to 'artist'
    navigate('/admindashboard'); // Navigate to the "/artist" route
  };

  return (
    <div className='container'>
      <hr></hr>
      {/* Show buttons only if a role is not selected */}
      {selectedRole === null && (
        <div style={{ fontSize: 'x-large', textAlign: 'center' }}>
          <button
            className='btn waves-effect waves-light #e53935 light-blue darken-1'
            onClick={handleUserButtonClick} // Call the User button click handler
          >
            User
          </button>
          |
          <button
            className='btn waves-effect waves-light'
            onClick={handleArtistButtonClick} // Call the Artist button click handler
          >
            Artist
          </button>
		  |
          <button
            className='btn waves-effect waves-light'
            onClick={handleAdminButtonClick} // Call the Admin button click handler
          >
            Admin
          </button>
        </div>
      )}
      <hr></hr>
      {/* Render UserLandingComponent or ArtistLandingComponent based on selectedRole */}
      {selectedRole === 'user' && <UserLandingComponent />}
      {selectedRole === 'artist' && <ArtistLandingComponent />}
	  {selectedRole === 'admin' && <AdminLandingComponent />}
      <hr></hr>
    </div>
  );
}

export default Landing;
