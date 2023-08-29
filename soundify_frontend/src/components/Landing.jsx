import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ArtistLandingComponent from './Artist/ArtistLanding';
import UserLandingComponent from './User/UserLanding';
import AdminLandingComponent from './Admin/AdminLanding';
import Header from './Header';
import backgroundImage from '../assets/background.jpg'; // Import your background image
import './Landing.css'; // Import your custom styles for the landing page

function Landing() {
  const navigate = useNavigate();
  const [selectedRole, setSelectedRole] = useState(null);

  const handleRoleButtonClick = (role) => {
    setSelectedRole(role);
    navigate(`/${role}login`);
  };

  return (
    <div className="landing-page" style={{ backgroundImage: `url(${backgroundImage})` }}>
      <Header />
      <div className="container">
        <hr />
        {selectedRole === null && (
          <div className="role-buttons">
            <button
              className="btn waves-effect waves-light"
              onClick={() => handleRoleButtonClick('user')}
            >
              User
            </button>
            <button
              className="btn waves-effect waves-light"
              onClick={() => handleRoleButtonClick('artist')}
            >
              Artist
            </button>
            <button
              className="btn waves-effect waves-light"
              onClick={() => handleRoleButtonClick('admin')}
            >
              Admin
            </button>
          </div>
        )}
        <hr />
        {selectedRole === 'user' && <UserLandingComponent />}
        {selectedRole === 'artist' && <ArtistLandingComponent />}
        {selectedRole === 'admin' && <AdminLandingComponent />}
        <hr />
      </div>
    </div>
  );
}

export default Landing;
