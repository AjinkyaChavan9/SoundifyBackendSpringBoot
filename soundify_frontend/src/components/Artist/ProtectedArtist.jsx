import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';


export default function ProtectedArtist(props) {
   const {Component} = props;
   const navigate = useNavigate();

   useEffect(()=>{
    let login = sessionStorage.getItem("artistIsLoggedIn");
    console.log(login);
    if(login == "false" || login==null){
        navigate('/artistlogin');
    }
   });

  return (
    <div><Component/></div>
  )
}
