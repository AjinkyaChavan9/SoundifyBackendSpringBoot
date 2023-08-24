import React, { useState, useEffect } from 'react';
import axios from 'axios';

function AllArtists() {
  const [artists, setArtists] = useState([]);

  useEffect(() => {
    fetchArtists();
  }, []);

  const fetchArtists = () => {
    axios.get('http://127.0.0.1:8080/api/admin/artists')
      .then(response => {
        setArtists(response.data);
      })
      .catch(error => {
        console.error('Error fetching artists:', error);
      });
  };

  const handleDelete = (artistId) => {
    axios.delete(`http://127.0.0.1:8080/api/admin/artists/${artistId}`)
      .then(response => {
        console.log('Artist deleted:', response.data);
        fetchArtists(); // Refresh the user list after deletion
      })
      .catch(error => {
        console.error('Error deleting artist:', error);
      });
  };

  return (
    <div className="all-artists">
      <h2>All Artists</h2>
      <div className="artist-list">
        <table className="table">
          <thead>
            <tr>
              <th>Id</th>
              <th>First Name</th>
              <th>Last Name</th>
              <th>Email</th>
              <th>Date Of Birth</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            {artists.map((artist, index) => (
              <tr key={index}>
                <td>{artist.id}</td>
                <td>{artist.firstName}</td>
                <td>{artist.lastName}</td>
                <td>{artist.email}</td>
                <td>{artist.dateOfBirth}</td>
                <td>
                  <button className="btn waves-effect waves-light #e53935 red darken-1 btn-danger" onClick={() => handleDelete(artist.id)}>
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default AllArtists;
