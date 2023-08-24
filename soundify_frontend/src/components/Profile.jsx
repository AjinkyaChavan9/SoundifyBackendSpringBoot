import { useState, useEffect } from 'react';
import 'materialize-css/dist/css/materialize.min.css';
import M from 'materialize-css';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

export const UpdateProfile = () => {
    const [credentials, setCredentials] = useState({
        id: "",
        first_name: "",
        last_name: "",
        email: "",
        mobile: "",
    });

    const [message, setMessage] = useState({ text: '', isError: false });
    const navigate = useNavigate();

    useEffect(() => {
        M.AutoInit();
        fetchUserDetails();
    }, []);

    const fetchUserDetails = () => {
        const userId = sessionStorage.getItem('id');
        axios.get(`http://127.0.0.1:8080/api/users/${userId}`)
            .then((response) => {
                // console.log(response.data)
                console.log(response.data.data)
                // debugger;
                setCredentials(response.data.data[0]);
            })
            .catch((error) => {
                showMessage('Failed to fetch user details', true);
            });
    };

    const handleInputChange = (args) => {
        // debugger;
        var copyOfCredentials = {...credentials}
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
        axios.put(`http://127.0.0.1:9898/user/${credentials.id}`,credentials)
            .then((response) => {
                showMessage('Profile updated successfully', false);
                navigate('/profile');
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
                            id="first_name"
                            name="first_name"
                            type="text"
                            className="validate"
                            value={credentials.first_name}
                            onChange={handleInputChange}
                        />
                        <label htmlFor="first_name">First Name</label>
                    </div>
                    <div className="input-field col s6">
                        <input
                            id="last_name"
                            name="last_name"
                            type="text"
                            className="validate"
                            value={credentials.last_name}
                            onChange={handleInputChange}
                        />
                        <label htmlFor="last_name">Last Name</label>
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
                            id="mobile"
                            name="mobile"
                            type="number"
                            className="validate"
                            value={credentials.mobile}
                            onChange={handleInputChange}
                        />
                        <label htmlFor="mobile">Mobile</label>
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
