import React, { useState } from 'react';
// import UsersSection from './UsersSection';
// import ArtistsSection from './ArtistsSection';
// import GenresSection from './GenresSection';

function AdminDashboard({ adminName }) {
  const [activeSection, setActiveSection] = useState('users');

  const handleSectionChange = (section) => {
    setActiveSection(section);
  };

  return (
    <div className="admin-dashboard">
      <header>
        <h1>Welcome, {adminName}!</h1>
      </header>
      <nav>
        <ul>
          <li>
            <button onClick={() => handleSectionChange('users')}>Users</button>
          </li>
          <li>
            <button onClick={() => handleSectionChange('artists')}>Artists</button>
          </li>
          <li>
            <button onClick={() => handleSectionChange('genres')}>Genres</button>
          </li>
        </ul>
      </nav>
      <main>
        {activeSection === 'users' && <UsersSection />}
        {activeSection === 'artists' && <ArtistsSection />}
        {activeSection === 'genres' && <GenresSection />}
      </main>
      <footer>
        <p>&copy; {new Date().getFullYear()} Admin Dashboard</p>
      </footer>
    </div>
  );
}

export default AdminDashboard;
