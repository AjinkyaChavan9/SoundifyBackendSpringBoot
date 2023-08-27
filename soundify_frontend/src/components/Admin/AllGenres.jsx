import React, { useState, useEffect } from 'react';
import axios from 'axios';

function GenreDashboard() {
 // const genreId = window.sessionStorage.getItem("id");

    const [genres, setGenres] = useState([]);
    const [editedGenre, setEditedGenre] = useState(null);

    useEffect(() => {
        fetchGenres();
    }, []);

    const fetchGenres = () => {
      //console.log("genre id", genreId);
        axios.get('http://127.0.0.1:8080/api/genre') // Adjust the API endpoint
            .then(response => {
                setGenres(response.data);
            })
            .catch(error => {
                console.error('Error fetching genres:', error);
            });
    };

    const handleEdit = (genreId) => {
        setEditedGenre(genreId);
    };

    const handleSave = (genreId) => {
        const editedGenreData = genres.find(genre => genre.id === genreId);

        axios.put(`http://127.0.0.1:8080/api/admin/genre/${genreId}`, editedGenreData) // Adjust the API endpoint
            .then(response => {
                console.log('Genre metadata updated:', response.data);
                fetchGenres(); // Refresh the genre list after editing
                setEditedGenre(null); // Clear the edited genre state
            })
            .catch(error => {
                console.error('Error updating genre metadata:', error);
            });
    };

    const handleDelete = (genreId) => {
        axios.delete(`http://127.0.0.1:8080/api/admin/genre/${genreId}`) // Adjust the API endpoint
            .then(response => {
                console.log('Genre deleted:', response.data);
                fetchGenres(); // Refresh the genre list after deletion
            })
            .catch(error => {
                console.error('Error deleting genre:', error);
            });
    };

    return (
        <div className="container">
            <h2 className="my-4">All Genres</h2>
            <div className="table-responsive">
                <table className="table table-striped">
                    <thead>
                        <tr>
                            <th>Id</th>
                            <th>Genre Name</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {genres.map(genre => (
                            <tr key={genre.id}>
                                <td>{genre.id}</td>
                                <td>
                                    {editedGenre === genre.id ? (
                                        <input
                                            type="text"
                                            value={genre.genreName}
                                            onChange={e => {
                                                const updatedGenres = genres.map(g =>
                                                    g.id === genre.id ? { ...g, genreName: e.target.value } : g
                                                );
                                                setGenres(updatedGenres);
                                            }}
                                        />
                                    ) : (
                                        genre.genreName
                                    )}
                                </td>
                                <td>
                                    {editedGenre === genre.id ? (
                                        <>
                                            <button className="btn btn-success" onClick={() => handleSave(genre.id)}>
                                                Save
                                            </button>
                                            <button className="btn btn-secondary mx-2" onClick={() => setEditedGenre(null)}>
                                                Cancel
                                            </button>
                                        </>
                                    ) : (
                                        <>
                                            <button className="btn btn-primary" onClick={() => handleEdit(genre.id)}>
                                                Edit
                                            </button>
                                            <button className="btn btn-danger mx-2" onClick={() => handleDelete(genre.id)}>
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

export default GenreDashboard;
