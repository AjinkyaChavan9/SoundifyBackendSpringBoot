import React, { Component, useEffect, useState } from 'react';
import Header from '../Header';
// import Footer from './Footer';
import { Link, Outlet, Route, Routes } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import '../../../node_modules/bootstrap/dist/css/bootstrap.min.css'
import About from '../About';
import Home from '../Home';
// import Contact from './Contact';
import Dashboard from './UserDashboard';
import NotFound from '../NotFound';

import Playlist from './Playlist';
import { SignUp } from './UserSignUp';
import { UpdateProfile } from './UserProfile';
import Login from './UserLogin';
import EditPlaylist from './EditPlaylist';
import ProtectedUser from './ProtectedUser';
import Landing from '../Landing';


function UserLanding() {
    //debugger;
    const [userIsLoggedInLanding, setUserIsLoggedInLanding] = useState("false");
    // useState(window.sessionStorage.getItem("userIsLoggedIn"))
   // window.sessionStorage.setItem("userIsLoggedIn", "false");

    var navigate = useNavigate();

    var Signup = () => {
        // debugger;
        navigate("/userregister");
    }

    var LogIn = () => {
        // debugger;  
        navigate("/userlogin");
    }
    var changeUserIsLoggedInLanding = () => {
        setUserIsLoggedInLanding(window.sessionStorage.getItem("userIsLoggedIn"))

    }

    useEffect(() => {
        changeUserIsLoggedInLanding();
    }, [userIsLoggedInLanding])

    var LogOut = () => {
        // debugger;

        window.sessionStorage.setItem("userIsLoggedIn", "false");
        // var isLogged =  window.sessionStorage.getItem("userIsLoggedIn")
        window.sessionStorage.setItem("firstName", "")
        window.sessionStorage.setItem("id", "");
        window.sessionStorage.setItem("email", "");


        changeUserIsLoggedInLanding();

        navigate("/landing");


    }
    return (
        <div className='container-fluid'>
            {/* <Header></Header> */}
            <hr></hr>
            <div style={{ fontSize: "x-large", textAlign: "center" }}>


                {userIsLoggedInLanding == "false" ?
                    (<><button className='btn waves-effect waves-light #e53935 light-blue darken-1 '
                        onClick={Signup}>Register</button>|
                        <button className='btn waves-effect waves-light '
                            onClick={LogIn}>Login</button> </>)
                    : (
                        <>
                        {/* <Link to="/" >Home</Link>| */}
                            <Link to="/userdashboard">Home</Link>|
                            <Link to="/userprofile">Profile</Link>|
                            <Link to="/playlist">Playlist</Link>|
                            <button className='btn waves-effect waves-light #e53935 red darken-1 btn-danger'
                                onClick={LogOut}>Logout</button></>)}
            </div>

            <hr></hr>
            <Routes>
                <Route path="/" element={<Outlet />}>
                    <Route index element={<Dashboard />} />
                    <Route path="userdashboard" element={<ProtectedUser Component={Dashboard}/>} />
                    <Route path="userprofile" element={<ProtectedUser Component={UpdateProfile} />} />
                    <Route path="userlogin" element={<Login userIsLoggedInLanding={userIsLoggedInLanding} changeUserIsLoggedInLanding={changeUserIsLoggedInLanding} />} />
                    <Route path="userregister" element={<SignUp />}/>
                    <Route path="playlist" element={<ProtectedUser Component={Playlist} />}/>
                    <Route path="editPlaylist" element={<ProtectedUser Component={EditPlaylist}/>}/>
                    <Route path="landing" element={<Landing/>}/>
                    <Route path="*" element={<NotFound />} />
                </Route>
            </Routes>

            {/* <Footer/> */}
        </div>
    );

}

export default UserLanding;