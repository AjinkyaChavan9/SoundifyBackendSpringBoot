import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useLocation } from 'react-router-dom';

import axios from 'axios';
import "../../../node_modules/materialize-css/dist/css/materialize.min.css"
import "materialize-css"


export default function EditPlaylist() {
  const [songs,setSongs] = useState([]);
  const [playlist,setPlaylist] = useState([]);
  const [songName,setSongName] = useState("");
  const [message, setMessage] = useState("");
  const location = useLocation();
  const playlistId = location.state?.playlistId;
  const playlistName = location.state?.pName;

     useEffect(() => {
     // Fetch songs from the API
     fetch('http://localhost:8080/api/songs/songs')
     .then(response => response.json())
     .then(data => {
      setSongs(data);
      console.log(data); // Check if data is received properly
     })
      .catch(error => console.error('Error fetching songs:', error));
    }, []);

    useEffect(() => {
        // Fetch playlist from the API
        fetch(`http://localhost:8080/api/playlists/songs/${playlistId}`)
        .then(response => response.json())
        .then(data => {
         setPlaylist(data);
         console.log(data); // Check if data is received properly
        })
         .catch(error => console.error('Error fetching songs:', error));
       }, []);

    const refresh = () => {
        fetch(`http://localhost:8080/api/playlists/songs/${playlistId}`)
        .then(response => response.json())
        .then(data => {
         setPlaylist(data);
         console.log(data); // Check if data is received properly
        })
         .catch(error => console.error('Error fetching songs:', error));
    }
   

    const addToPlaylist = (songId) =>{
        axios.post(`http://localhost:8080/api/playlists/${playlistId}/song/${songId}`, {
            headers: { "content-type": "application/json" },
        }).then((response) => {
            const result = response.data;

            if (result.status == "success") {
                refresh();
                console.log("song sdded successfully");
                showMessage("song added successfully");
                
            }
        }).catch((error) => {
            console.log(error);
            console.error("An error occurred:", error);
            showMessage("An error occurred while logging in");
        });
    }

    const removeFromPlaylist = (songId) =>{
        axios.delete(`http://localhost:8080/api/playlists/${playlistId}/song/${songId}`, {
            headers: { "content-type": "application/json" },
        }).then((response) => {
            const result = response.data;

            if (result.status == "success") {
                refresh();
                console.log("song sdded successfully");
                showMessage("song removed successfully");
                
            }
        }).catch((error) => {
            console.log(error);
            console.error("An error occurred:", error);
            showMessage("An error occurred while logging in");
        });
    }

    const onTextChange = (event) => {
        setSongName(event.target.value);
    }

    const showMessage = (message) => {
        setMessage(message);
        setTimeout(() => { setMessage("") }, 3000);
    }

  return (
    <div className='container-fluid'>
        <div style={{ display: 'flex', flexDirection: 'row', marginTop: '20px' }}>
                <h3 style={{ flex: '1' }}>{playlistName}</h3>
            </div>
            <div style={{ flex: '0 0 50%', paddingRight: '10px', display: 'flex', flexDirection: 'column', alignItems: 'flex-start' }}>
                <div className="input-field col s12">
                    <input
                        type="text"
                        name="songName"
                        value={songName}
                        onChange={onTextChange}
                    />
                    <label htmlFor="songName">Search Song Name</label>
                </div>
            </div>
    <div className='container-fluid' style={{ display: 'flex', flexDirection: 'row' }}>
    <div style={{ flex: '0 0 50%', paddingRight: '10px' }}>
        
        <table className='table-striped'>
        <thead>
            <tr>
                <th>Song Name</th>
                <th>Artist</th>
                <th>Add</th>
            </tr>
        </thead>
        <tbody>
            {
             songs.map((song) =>{
                if(songName != "") 
                {
            if(song.songName.toLowerCase().includes(songName.toLocaleLowerCase())
            || song.artistName.toLowerCase().includes(songName.toLocaleLowerCase()))
                {
                 return <tr key={song.id}>
                    <td>{song.songName}</td>
                    <td>{song.artistName}</td>
                    <td>
                        <button className="btn waves-effect waves-light"
                         onClick={()=>{
                            addToPlaylist(song.id);
                         }}>
                            +
                        </button>
                    </td>
                </tr>
                }
            }
            else
            {
                return <tr key={song.id}>
                    <td>{song.songName}</td>
                    <td>{song.artistName}</td>
                    <td>
                        <button className="btn waves-effect waves-light"
                         onClick={()=>{
                            addToPlaylist(song.id);
                         }}>
                            +
                        </button>
                    </td>
                </tr>
            }
          })
        }    
        </tbody>
       </table>
       {message && (
                <div className="row">
                    <div className="col s12">
                        <p className="message">{message}</p>
                    </div>
                </div>
            )}
    </div>
    <div style={{ flex: '1' }}>
    <table className='table-striped'>
                <thead>
                    <tr>
                        <th>Song Name</th>
                        <th>Artist</th>
                        <th>Duration</th>
                        <th>Remove</th>
                    </tr>
                </thead>
                <tbody>
                    {playlist.map((song, index) => (
                        <tr key={song.id}>
                            <td>{song.songName}</td>
                            <td>{song.artistName}</td>
                            <td>{song.duration}</td>
                            <td >
                            <button className="btn waves-effect waves-light red"
                                onClick={()=>{
                                removeFromPlaylist(song.id);
                                }}>
                                  X
                            </button>
                            </td>
                            <td>
                               
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
    </div>
    </div>
    </div>
  )
}
