import React, { useEffect, useState } from 'react';
import { Link, Outlet, Route, Routes, useParams, useNavigate } from 'react-router-dom';
import Home from '../Home';
import Dashboard from './ArtistDashboard';
import NotFound from '../NotFound';
import { SignUp } from './ArtistSignUp';
import { UpdateProfile } from './ArtistProfile';
import Login from './ArtistLogin';
import ArtistUploadSong from './ArtistUploadSong';

function ArtistLanding() {
    // Get the artistId from route parameters
    const artistId = window.sessionStorage.getItem("id");

    console.log('artistId:', artistId); // Check the value of artistId

    const [artistIsLoggedInLanding, setArtistIsLoggedInLanding] = useState("false");
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

    const LogOut = () => {
        window.sessionStorage.setItem("artistIsLoggedIn", "false");
        window.sessionStorage.setItem("firstName", "");
        window.sessionStorage.setItem("id", "");
        window.sessionStorage.setItem("email", "");

        changeArtistIsLoggedInLanding();
        navigate("/landing");
    };

    return (
        <div className='container'>
            <hr />
            <div style={{ fontSize: "x-large", textAlign: "center" }}>
                {artistIsLoggedInLanding === "false" ?
                    (<>
                        <button className='btn waves-effect waves-light #e53935 light-blue darken-1' onClick={Signup}>Register</button>|
                        <button className='btn waves-effect waves-light' onClick={LogIn}>Login</button>
                    </>)
                    : (
                        <>
                            <Link to="/">Home</Link>|
                            <Link to="/artistdashboard">My Songs</Link>|
                            <Link to={`/upload/${artistId}`}>Upload a Song</Link>|
                            <Link to="/artistprofile">Profile</Link>|
                            <button className='btn waves-effect waves-light #e53935 red darken-1 btn-danger' onClick={LogOut}>Logout</button>
                        </>
                    )}
            </div>
            <hr />
            <Routes>
                <Route path="/" element={<Outlet />}>
                    <Route index element={<Home />} />
                    <Route path="artistdashboard" element={<Dashboard artistIsLoggedInLanding={artistIsLoggedInLanding} changeArtistIsLoggedInLanding={changeArtistIsLoggedInLanding} />} />
                    <Route path="artistprofile" element={<UpdateProfile />} />
                    <Route path="/upload/:artistId" element={<ArtistUploadSong />} />
                    <Route path="artistlogin" element={<Login artistIsLoggedInLanding={artistIsLoggedInLanding} changeArtistIsLoggedInLanding={changeArtistIsLoggedInLanding} />} />
                    <Route path="artistregister" element={<SignUp />} />
                    <Route path="*" element={<NotFound />} />
                </Route>
            </Routes>
        </div>
    );
}

export default ArtistLanding;
