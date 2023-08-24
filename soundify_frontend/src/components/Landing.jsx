import React, { Component, useEffect, useState } from 'react';
import Header from './Header';
// import Footer from './Footer';
import { Link, Outlet, Route, Routes } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';
import '../../node_modules/bootstrap/dist/css/bootstrap.min.css'
import About from './About';
import Home from './Home';
// import Contact from './Contact';
import Dashboard from './Dashboard';
import NotFound from './NotFound';
import ProtectedRoute from './ProtectedRoute';

import { SignUp } from './SignUp';
import { UpdateProfile } from './Profile';
import Login from './Login';

function Landing() {
    //debugger;
    const [userIsLoggedInLanding, setUserIsLoggedInLanding] = useState("false");
    // useState(window.sessionStorage.getItem("userIsLoggedIn"))


    var navigate = useNavigate();

    var Signup = () => {
        // debugger;
        navigate("/register");
    }

    var LogIn = () => {
        // debugger;  
        navigate("/login");
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

        navigate("/login");


    }
    return (
        <div className='container'>
            <Header></Header>
            <hr></hr>
            <div style={{ fontSize: "x-large", textAlign: "center" }}>


                {userIsLoggedInLanding == "false" ?
                    (<><button className='btn waves-effect waves-light #e53935 light-blue darken-1 '
                        onClick={Signup}>Register</button>|
                        <button className='btn waves-effect waves-light '
                            onClick={LogIn}>Login</button> </>)
                    : (
                        <><Link to="/" >Home</Link>|
                            <Link to="/dashboard">All Songs</Link>|
                            <Link to="/profile">Profile</Link>|
                            <button className='btn waves-effect waves-light #e53935 red darken-1 btn-danger'
                                onClick={LogOut}>Logout</button></>)}
            </div>

            <hr></hr>
            <Routes>
                <Route path="/" element={<Outlet />}>
                    <Route index element={<Home />} />
                    <Route path="dashboard" element={<Dashboard userIsLoggedInLanding={userIsLoggedInLanding} changeUserIsLoggedInLanding={changeUserIsLoggedInLanding} />} />
                    <Route path="profile" element={<UpdateProfile />} />
                    <Route path="login" element={<Login userIsLoggedInLanding={userIsLoggedInLanding} changeUserIsLoggedInLanding={changeUserIsLoggedInLanding} />} />
                    <Route path="register" element={<SignUp />} />
                    <Route path="*" element={<NotFound />} />
                </Route>
            </Routes>

            {/* <Footer/> */}
        </div>
    );

}

export default Landing;