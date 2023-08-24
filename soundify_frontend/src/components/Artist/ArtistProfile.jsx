import { useState, useEffect } from 'react';
import 'materialize-css/dist/css/materialize.min.css';
import M from 'materialize-css';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

export const UpdateProfile = () => {
    const [credentials, setCredentials] = useState({
        id: "",
        name: "",
        firstName: "",
        lastName: "",
        email: "",
        dateOfBirth: "",
    });

    const [message, setMessage] = useState({ text: '', isError: false });
    const navigate = useNavigate();

    useEffect(() => {
        M.AutoInit();
        fetchUserDetails();
    }, []);

    const fetchUserDetails = () => {
        const artistId = sessionStorage.getItem('id');
        axios.get(`http://127.0.0.1:8080/api/artists/${artistId}`)
            .then((response) => {
                // console.log(response.data)
                console.log(response)
                console.log(response.data);
                // debugger;
                setCredentials(response.data);
            })
            .catch((error) => {
                console.log(error)
                showMessage('Failed to fetch user details', true);
            });
    };

    const handleInputChange = (args) => {
        // debugger;
        var copyOfCredentials = { ...credentials }
        copyOfCredentials[args.target.name] = args.target.value;
        setCredentials(copyOfCredentials);
    };


    const showMessage = (message, isError) => {
        setMessage({ text: message, isError: isError });
        setTimeout(() => {
            setMessage('');
        }, 3000);
    };
    const handleUpdateProfile = () => {
        console.log(credentials)
        axios.put(`http://127.0.0.1:8080/api/artists/${credentials.id}`, credentials)
            .then((response) => {
                showMessage('Profile updated successfully', false);
                navigate('/artistprofile');
            })
            .catch((error) => {
                showMessage('Failed to update profile', true);
            });
    };

    return (
        <div className="container">
            <form>
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
                            onChange={handleInputChange}
                        />
                        <label htmlFor="name">Artist Name</label>
                    </div>
                </div>


                <div className="row">
                    <div className="input-field col s6">
                        <input
                            id="lastName"
                            name="firstName"
                            type="text"
                            className="validate"
                            value={credentials.firstName}
                            onChange={handleInputChange}
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
                            onChange={handleInputChange}
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
                            onChange={handleInputChange}
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
                            onChange={handleInputChange}
                        />
                        <label htmlFor="dateOfBirth">Date of Birth</label>
                    </div>
                </div>

                <div className="text-center">
                    <button
                        type="button"
                        className="btn btn-primary"
                        onClick={handleUpdateProfile}
                    >
                        Update Profile
                    </button>
                </div>
                {message.text && (
                    <div
                        className={`card-panel ${message.isError ? 'red' : 'teal'
                            } lighten-2`}
                    >
                        {message.text}
                    </div>
                )}
            </form>
        </div>
    );
};
export default UpdateProfile;