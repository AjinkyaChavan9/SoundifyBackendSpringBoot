import React, { useState, useEffect } from 'react';
import axios from 'axios';

function GenreDashboard() {
    // const genreId = window.sessionStorage.getItem("id");

    const [genres, setGenres] = useState([]);
    const [editedGenre, setEditedGenre] = useState(null);
    const [updatedGenreName, setUpdatedGenreName] = useState('');
    const [newGenreName, setNewGenreName] = useState('');


    useEffect(() => {
        fetchGenres();
    }, []);

    const fetchGenres = () => {
        //console.log("genre id", genreId);
        axios.get('/api/genre') // Adjust the API endpoint
            .then(response => {
                setGenres(response.data);
            })
            .catch(error => {
                console.error('Error fetching genres:', error);
            });
    };
    const handleAdd = () => {
        axios.post('/api/admin/genre', null, {
            params: {
                genreName: newGenreName
            }
        })
            .then(response => {
                console.log('Genre added:', response.data);
                fetchGenres();
                setNewGenreName('');
            })
            .catch(error => {
                console.error('Error adding genre:', error);
            });
    };

    const handleEdit = (genreId) => {
        const editedGenreData = genres.find(genre => genre.id === genreId);

        setUpdatedGenreName(editedGenreData.genreName);
        setEditedGenre(genreId);
    };

    const handleSave = (genreId) => {
        axios.put(`/api/admin/genre/${genreId}`, null, {
            params: {
                updatedGenreName: updatedGenreName
            }
        })
            .then(response => {
                console.log('Genre metadata updated:', response.data);
                fetchGenres();
                setEditedGenre(null);
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
        <div className="container-fluid">
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
                                            value={updatedGenreName}
                                            onChange={e => setUpdatedGenreName(e.target.value)}
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
                                            <button className="btn waves-effect waves-light #e53935 red darken-1 mx-2" onClick={() => setEditedGenre(null)}>
                                                Cancel
                                            </button>
                                        </>
                                    ) : (
                                        <>
                                            <button className="btn waves-effect waves-light #f57f17 yellow darken-4 btn-warning" onClick={() => handleEdit(genre.id)}>
                                                Edit
                                            </button>
                                            <button className=" btn waves-effect waves-light #e53935 red darken-1 mx-2" onClick={() => handleDelete(genre.id)}>
                                                Delete
                                            </button>
                                        </>
                                    )}
                                </td>
                            </tr>
                        ))}
                        <tr>
                            <td></td>
                            <td>
                                <input
                                    type="text"
                                    placeholder="Enter new genre name"
                                    value={newGenreName}
                                    onChange={e => setNewGenreName(e.target.value)}
                                />
                            </td>
                            <td>
                                <button className="btn btn-success" onClick={handleAdd}>
                                    Add
                                </button>
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    );

}

export default GenreDashboard;
