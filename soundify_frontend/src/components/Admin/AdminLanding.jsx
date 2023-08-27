import React, { Component, useEffect, useState } from 'react';
import Header from '../Header';
// import Footer from './Footer';
import { Link, Outlet, Route, Routes } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import '../../../node_modules/bootstrap/dist/css/bootstrap.min.css'
import About from '../About';
import Home from '../Home';
// import Contact from './Contact';
import Dashboard from './AdminDashboard';
import NotFound from '../NotFound';
import ProtectedRoute from '../ProtectedRoute';

import Login from './AdminLogin';
import { AdminProfile } from './AdminProfile';
import AllUsers from './AllUsers';
import AllArtists from './AllArtists';
import AllGenres from './AllGenres';
function AdminLanding() {
    //debugger;
    const [userIsLoggedInLanding, setUserIsLoggedInLanding] = useState("false");
    // useState(window.sessionStorage.getItem("userIsLoggedIn"))


    var navigate = useNavigate();


    var LogIn = () => {
        // debugger;  
        navigate("/adminlogin");
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

        changeUserIsLoggedInLanding();

        navigate("/adminlogin");


    }
    return (
        <div className='container'>
            {/* <Header></Header> */}
            <hr></hr>
            <div style={{ fontSize: "x-large", textAlign: "center" }}>


                {userIsLoggedInLanding == "false" ?
                    (<> <button className='btn waves-effect waves-light '
                            onClick={LogIn}>Login</button> </>)
                    : (
                        <><Link to="/" >Home</Link>|
                            <Link to="/admindashboard">Admin DashBoard</Link>|
                            <Link to="/adminprofile">Profile</Link>|
                            <button className='btn waves-effect waves-light #e53935 red darken-1 btn-danger'
                                onClick={LogOut}>Logout</button></>)}
            </div>

            <hr></hr>
            <Routes>
                <Route path="/" element={<Outlet />}>
                    <Route index element={<Home />} />
                    <Route path="admindashboard" element={<Dashboard userIsLoggedInLanding={userIsLoggedInLanding} changeUserIsLoggedInLanding={changeUserIsLoggedInLanding} />} />
                    <Route path="adminprofile" element={<AdminProfile />} />
                    <Route path="adminlogin" element={<Login userIsLoggedInLanding={userIsLoggedInLanding} changeUserIsLoggedInLanding={changeUserIsLoggedInLanding} />} />
                    <Route path="admindashboard/users" element={<AllUsers />} />
                    <Route path="admindashboard/artists" element={<AllArtists />} />
                    <Route path="admindashboard/genres" element={<AllGenres />} />
                    <Route path="*" element={<NotFound />} />
                </Route>
            </Routes>

            {/* <Footer/> */}
        </div>
    );

}

export default AdminLanding;