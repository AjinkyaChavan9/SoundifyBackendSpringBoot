import React, { useEffect, useState } from 'react';
import { Link, Outlet, Route, Routes, useNavigate } from 'react-router-dom';
import Home from '../Home';
import NotFound from '../NotFound';
import { SignUp } from './ArtistSignUp';
import { UpdateProfile } from './ArtistProfile';
import Login from './ArtistLogin';
import ArtistUploadSong from './ArtistUploadSong';
import Landing from '../Landing';

import ArtistUploadProfilePic from './ArtistUploadProfilePic';
import axios from 'axios';
import defaultProfileImage from './assets/singer-avatar.webp'
import ProtectedArtist from './ProtectedArtist';
import ArtistDashboard from './ArtistDashboard';


function ArtistLanding() {
    // Get the artistId from route parameters
    const artistId = window.sessionStorage.getItem("id");
    console.log('artistId:', artistId); // Check the value of artistId

    const [artistIsLoggedInLanding, setArtistIsLoggedInLanding] = useState("false");

    const [profileImage, setProfileImage] = useState(null); // State for profile image
    const [forceRerender, setForceRerender] = useState(false);




    const navigate = useNavigate();

    const Signup = () => {
        navigate("/artistregister");
    };

    const LogIn = () => {
        navigate("/artistlogin");
    };

    const changeArtistIsLoggedInLanding = () => {
        setArtistIsLoggedInLanding(window.sessionStorage.getItem("artistIsLoggedIn"));
    };

    useEffect(() => {
        changeArtistIsLoggedInLanding();
    }, [artistIsLoggedInLanding]);

    useEffect(() => {
        changeArtistIsLoggedInLanding();

        // Fetch the artist's profile image using the artistId
        const fetchProfileImage = async () => {
            try {
                const response = await axios.get(`http://127.0.0.1:8080/api/artists/${artistId}/image`, {
                    responseType: 'arraybuffer', // Specify responseType as 'arraybuffer' to handle image data
                });

                if (response.status === 200) {
                    // Create a blob from the image data and set it in the state
                    const blob = new Blob([response.data], { type: response.headers['content-type'] });
                    const imageUrl = URL.createObjectURL(blob);
                    setProfileImage(imageUrl);

                }
            } catch (error) {
                console.error('Error fetching profile image', error);
                // Set the default profile image if fetching fails
                setProfileImage(defaultProfileImage); // Replace with the actual path
            }
        };


        fetchProfileImage();

    }, [artistId, artistIsLoggedInLanding]);




    const LogOut = () => {
        window.sessionStorage.clear();
        window.sessionStorage.setItem("artistIsLoggedIn", "false");
        window.sessionStorage.setItem("firstName", "");
        window.sessionStorage.setItem("id", "");
        window.sessionStorage.setItem("email", "");

        changeArtistIsLoggedInLanding();
        //navigate("/");
        window.location.href = "/";
    };

    return (
        <div className='container-fluid'>
            <hr />
            <div style={{ fontSize: "x-large", textAlign: "center" }}>
                {artistIsLoggedInLanding === "false" ?
                    (<>
                        <button className='btn waves-effect waves-light #e53935 light-blue darken-1' onClick={Signup}>Register</button>|
                        <button className='btn waves-effect waves-light' onClick={LogIn}>Login</button>
                    </>)
                    : (
                        <>
                            <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                                {/* Display the profile image if available, or the default image */}
                                <>
                                    {profileImage ? (
                                        <img src={profileImage} alt='Profile' style={{
                                            maxWidth: '100px', maxHeight: '100px', borderRadius: '50%',
                                            border: '2px solid #ccc',
                                        }} />
                                    ) : (
                                        // Display the default image if profileImage is not available
                                        <>
                                            <img
                                                src={defaultProfileImage}
                                                alt='Default Profile'
                                                style={{
                                                    maxWidth: '100px',
                                                    maxHeight: '100px',
                                                    borderRadius: '50%',
                                                    border: '2px solid #ccc',
                                                }}
                                            />
                                        </>
                                    )}
                                </>

                                <Link to="/">Home</Link>|
                                <Link to="/artistdashboard">My Songs</Link>|
                                <Link to={`/upload/${artistId}`}>Upload Song</Link>|
                                <Link to={`/uploadpic/${artistId}`}>Upload Profile Pic</Link>|
                                <Link to="/artistprofile">Profile</Link>|
                                <button className='btn waves-effect waves-light #e53935 red darken-1 btn-danger' onClick={LogOut}>Logout</button>
                            </div>


                        </>
                    )}
            </div>
            <hr />
            <Routes>
                <Route path="/" element={<Outlet />}>
                    <Route index element={<Home />} />
                    <Route path="artistdashboard" element={<ProtectedArtist Component={ArtistDashboard} />} />
                    <Route path="artistprofile" element={<ProtectedArtist Component={UpdateProfile} />} />
                    <Route path="/upload/:artistId" element={<ProtectedArtist Component={ArtistUploadSong} />} />
                    <Route path="/uploadpic/:artistId" element={<ProtectedArtist Component={ArtistUploadProfilePic} />} />
                    <Route path="artistlogin" element={<Login artistIsLoggedInLanding={artistIsLoggedInLanding} changeArtistIsLoggedInLanding={changeArtistIsLoggedInLanding} />} />
                    <Route path="artistregister" element={<SignUp />} />
                    <Route path="landing" element={< Landing />} />
                    <Route path="*" element={<NotFound />} />
                </Route>
            </Routes>
        </div>
    );
}

export default ArtistLanding;
