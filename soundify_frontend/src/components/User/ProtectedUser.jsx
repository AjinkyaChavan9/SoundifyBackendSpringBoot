import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';


export default function ProtectedUser(props) {
   const {Component} = props;
   const navigate = useNavigate();

   useEffect(()=>{
    let login = sessionStorage.getItem("userIsLoggedIn");
    console.log(login);
    if(login == "false" || login==null){
        navigate('/userlogin');
    }
   });

  return (
    <div><Component/></div>
  )
}
