import React, { useState } from 'react';
import { Routes, Route, Link, useLocation } from 'react-router-dom'; // Import Routes, Route, and Link from react-router-dom
import AllUsers from './AllUsers'; // Import the AllUsers component
import ArtistLanding from '../Artist/ArtistLanding';
//import GenreLanding from '../Genre/GenreLanding'; // Make sure to import GenreLanding if needed

function AdminDashboard({ adminName }) {
  //const [activeSection, setActiveSection] = useState('users');
  const location = useLocation();

  const getActiveSection = (path) => {
    if (path.startsWith('/admindashboard/users')) {
      return 'users';
    } else if (path.startsWith('/admindashboard/artists')) {
      return 'artists';
    } else if (path.startsWith('/admindashboard/genres')) {
      return 'genres';
    }
    return '';
  };

  const [activeSection, setActiveSection] = useState(getActiveSection(location.pathname));


  const handleSectionChange = (section) => {
    setActiveSection(section);
  };

  return (
    <div className="admin-dashboard">
      <header className="bg-light p-3">
        <h1 className="text-center">Welcome, {adminName}!</h1>
      </header>
      <nav className="navbar navbar-expand-lg navbar-light bg-light">
        <div className="container">
          <a className="navbar-brand" href="#">
            Select What You Want To View
          </a>
          
           
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav ml-auto">
              <li className="nav-item">
                <Link
                  to="/admindashboard/users"
                  className={`nav-link btn ${activeSection === 'users' ? 'active' : ''}`}
                  onClick={() => handleSectionChange('users')}
                >
                  Users
                </Link>
              </li>
              <li className="nav-item">
                <Link
                  to="/admindashboard/artists"
                  className={`nav-link btn ${activeSection === 'artists' ? 'active' : ''}`}
                  onClick={() => handleSectionChange('artists')}
                >
                  Artists
                </Link>
              </li>
              <li className="nav-item">
                <Link
                  to="/admindashboard/genres"
                  className={`nav-link btn ${activeSection === 'genres' ? 'active' : ''}`}
                  onClick={() => handleSectionChange('genres')}
                >
                  Genres
                </Link>
              </li>
            </ul>
          </div>
          
        </div>
      </nav>
      <main className="container mt-4">
        <Routes>
          <Route path="/admindashboard/users" element={<AllUsers />} />
          <Route path="/admindashboard/artists" element={<ArtistLanding />} />
          {/* Add similar routes for other sections if needed */}
        </Routes>
      </main>
      <footer className="mt-4 text-center py-3 bg-light">
        <p>&copy; {new Date().getFullYear()} Admin Dashboard</p>
      </footer>
    </div>
  );
}

export default AdminDashboard;
