import '../../../node_modules/bootstrap/dist/css/bootstrap.min.css'
import React, { useState, useEffect } from 'react';
import axios from 'axios';

function ArtistDashboard() {
    const artistId = window.sessionStorage.getItem("id");
    const [songs, setSongs] = useState([]);
    const [editedSong, setEditedSong] = useState(null);

    useEffect(() => {
        fetchSongs();
    }, []);

    const fetchSongs = () => {
        axios.get(`http://127.0.0.1:8080/api/artists/${artistId}/song`)
            .then(response => {
                setSongs(response.data);
            })
            .catch(error => {
                console.error('Error fetching songs:', error);
            });
    };

    const handleEdit = (songId) => {
        setEditedSong(songId);
    };

    const handleSave = (songId) => {
        const editedSongData = songs.find(song => song.id === songId);

        axios.put(`http://127.0.0.1:8080/api/artists/${artistId}/song/${songId}`, editedSongData)
            .then(response => {
                console.log('Song metadata updated:', response.data);
                fetchSongs(); // Refresh the song list after editing
                setEditedSong(null); // Clear the edited song state
            })
            .catch(error => {
                console.error('Error updating song metadata:', error);
            });
    };

    const handleDelete = (songId) => {
        axios.delete(`http://127.0.0.1:8080/api/artists/${artistId}/song/${songId}`)
            .then(response => {
                console.log('Song deleted:', response.data);
                fetchSongs(); // Refresh the song list after deletion
            })
            .catch(error => {
                console.error('Error deleting song:', error);
            });
    };

    return (
        <div className="container-fluid">
            <h2 className="my-4">All Songs by Artist</h2>
            <div className="table-responsive">
                <table className="table table-striped">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Song Name</th>
                            <th>Duration</th>
                            <th>Release Date</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {songs.map(song => (
                            <tr key={song.id}>
                                <td>{song.id}</td>
                                <td>
                                    {editedSong === song.id ? (
                                        <input
                                            type="text"
                                            value={song.songName}
                                            onChange={e => {
                                                const updatedSongs = songs.map(s =>
                                                    s.id === song.id ? { ...s, songName: e.target.value } : s
                                                );
                                                setSongs(updatedSongs);
                                            }}
                                        />
                                    ) : (
                                        song.songName
                                    )}
                                </td>
                                <td>
                                    {editedSong === song.id ? (
                                        <input
                                            type="text"
                                            value={song.duration}
                                            onChange={e => {
                                                const updatedSongs = songs.map(s =>
                                                    s.id === song.id ? { ...s, duration: e.target.value } : s
                                                );
                                                setSongs(updatedSongs);
                                            }}
                                        />
                                    ) : (
                                        song.duration
                                    )}
                                </td>
                                <td>
                                    {editedSong === song.id ? (
                                        <input
                                            type="text"
                                            value={song.releaseDate}
                                            onChange={e => {
                                                const updatedSongs = songs.map(s =>
                                                    s.id === song.id ? { ...s, releaseDate: e.target.value } : s
                                                );
                                                setSongs(updatedSongs);
                                            }}
                                        />
                                    ) : (
                                        song.releaseDate
                                    )}
                                </td>
                                <td>
                                    {editedSong === song.id ? (
                                        <>
                                            <button className="btn btn-success" onClick={() => handleSave(song.id)}>
                                                Save
                                            </button>
                                            <button className="btn btn-secondary mx-2" onClick={() => setEditedSong(null)}>
                                                Cancel
                                            </button>
                                        </>
                                    ) : (
                                        <>
                                            <button className="btn  waves-effect waves-dark  #ffff00 yellow accent-2 black-text text-darken-2" onClick={() => handleEdit(song.id)}>
                                                Edit
                                            </button>
                                            <button className="btn waves-effect waves-light #e53935 red darken-1 btn-danger mx-2" onClick={() => handleDelete(song.id)}>
                                                Delete
                                            </button>
                                        </>
                                    )}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default ArtistDashboard;





