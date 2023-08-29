import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

import ArtistLandingComponent from './Artist/ArtistLanding';
import UserLandingComponent from './User/UserLanding';
import AdminLandingComponent from './Admin/AdminLanding';
import Header from './Header';
//import backgroundImage from '../assets/logo/svg/logo-white.svg'; // Import your background image


function Landing() {
  const navigate = useNavigate();
  const location = useLocation();

  const [selectedRole, setSelectedRole] = useState(null);
  //const [reloadKey, setReloadKey] = useState(0); // Add a reload key

  // useEffect(() => {
  //   // Check if the route path is '/'
  //   if (location.pathname == '/landing') {
  //     setReloadKey(prevKey => prevKey + 1); // Update the reload key
  //   }
  // }, [location.pathname]);

  // Click handler for the User button
  const handleUserButtonClick = () => {
    setSelectedRole('user'); // Set selectedRole to 'user'
    navigate('/userlogin'); // Navigate to the "/user" route
  };

  // Click handler for the Artist button
  const handleArtistButtonClick = () => {
    setSelectedRole('artist'); // Set selectedRole to 'artist'
    navigate('/artistlogin'); // Navigate to the "/artist" route
  };

  const handleAdminButtonClick = () => {
    setSelectedRole('admin'); // Set selectedRole to 'admin'
    navigate('/adminlogin'); // Navigate to the "/admin" route
  };

  return (
    <div >
      <Header />
      <div className='container'  
      // style={{
      //     backgroundImage: `url(${backgroundImage})`,
      //     backgroundColor: 'rgba(0, 0, 0, 0.9)', // Adjust alpha value (0.5 for 50% opacity)
      //     backgroundSize: 'cover',
      //     backgroundRepeat: 'no-repeat',
      //     backgroundPosition: 'center center'}}
          >
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
    </div>
  );
}

export default Landing;
