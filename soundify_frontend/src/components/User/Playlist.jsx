import React, { useEffect, useRef, useState } from 'react';
import { Link,useNavigate } from 'react-router-dom';
import EditPlaylist from './EditPlaylist';
import axios from 'axios';
import "../../../node_modules/materialize-css/dist/css/materialize.min.css"
import "materialize-css"
import ReactH5AudioPlayer from 'react-h5-audio-player';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlay, faPause, faThumbsUp } from '@fortawesome/free-solid-svg-icons';

const AudioPlayer = ReactH5AudioPlayer;

function Playlist(){
    const [playlistName, setPlaylistName] = useState("");
    const [playlists,setPlaylists] = useState([]);
    const [playlist,setPlaylist] = useState([]);
    const navigate = useNavigate();
    const [message, setMessage] = useState("");
    const [playlistId,setplaylistId] = useState('');
    const [currentTrack, setTrackIndex] = useState(-1);
    const [isPlaying, setIsPlaying] = useState(false);
    const player = useRef();
    const [currentSongPage, setCurrentSongPage] = useState(1);
    const songsPerPage = 3;
    const startIndexSongs = (currentSongPage - 1) * songsPerPage;
    const endIndexSongs = startIndexSongs + songsPerPage;
    const songsToShow = playlist.slice(startIndexSongs, endIndexSongs);

    const [currentPlaylistPage, setCurrentPlaylistPage] = useState(1);
    const playlistsPerPage = 3;

    const startIndexPlaylists = (currentPlaylistPage - 1) * playlistsPerPage;
const endIndexPlaylists = startIndexPlaylists + playlistsPerPage;
const playlistsToShow = playlists.slice(startIndexPlaylists, endIndexPlaylists);


    
    const userId = window.sessionStorage.getItem("id");
   
    const createPlaylist = () => {
        axios.post(`http://localhost:8080/api/users/${userId}/playlist/${playlistName}`, {
            headers: { "content-type": "application/json" },
        }).then((response) => {
            const result = response.data;

            if (result.playlistName === playlistName) {
                console.log("playlist created successfully");
                showMessage("playlist "+result.playlistName+" created successfully");
                navigate('/EditPlaylist', { state: { playlistId: result.id ,pName: playlistName } });
            }
        }).catch((error) => {
            console.log(error);
            console.error("An error occurred:", error);
            showMessage("An error occurred while logging in");
        });
    }

    useEffect(() => {
        // Fetch playlist from the API
        fetch(`http://localhost:8080/api/playlists/user/${userId}`)
        .then(response => response.json())
        .then(data => {
         setPlaylists(data);
         console.log(data); // Check if data is received properly
        })
         .catch(error => console.error('Error fetching songs:', error));
       }, []);

    const refresh = ()=>{
        fetch(`http://localhost:8080/api/playlists/user/${userId}`)
        .then(response => response.json())
        .then(data => {
         setPlaylists(data);
         console.log(data); // Check if data is received properly
        })
         .catch(error => console.error('Error fetching songs:', error));
    }

    const playPlaylist = (playlistId) => {
        fetch(`http://localhost:8080/api/playlists/songs/${playlistId}`)
        .then(response => response.json())
        .then(data => {
         setPlaylist(data);
         console.log(data); // Check if data is received properly
        })
         .catch(error => console.error('Error fetching songs:', error));
    } 

    const removePlaylist = (playlistId) => {
        axios.delete(`http://localhost:8080/api/users/${userId}/playlist/${playlistId}`, {
            headers: { "content-type": "application/json" },
        }).then((response) => {
            const result = response.data;

            if (result.status == "success") {
                refresh();
                console.log("playlist removed successfully");
                showMessage("playlist removed successfully");
                
            }
        }).catch((error) => {
            console.log(error);
            console.error("An error occurred:", error);
            showMessage("An error occurred while logging in");
        });
    } 

    const editPlaylist = (playlistId,playlistName) =>{
        navigate('/EditPlaylist', { state: { playlistId: playlistId ,pName: playlistName } });
    }

    const onTextChange = (event) => {
        setPlaylistName(event.target.value);
    }
  
    const showMessage = (message) => {
        setMessage(message);
        setTimeout(() => { setMessage("") }, 3000);
    }

    const handleClickPrevious = () => {
        console.log("click previous");
        setTrackIndex(currentTrack => (currentTrack > 0 ? currentTrack - 1 : songs.length - 1));
    };

    const handleClickNext = () => {
        console.log("click next");

        setTrackIndex(currentTrack => (currentTrack < songs.length - 1 ? currentTrack + 1 : 0));
    };

    const handleEnd = () => {
        console.log("end");

        setTrackIndex(currentTrack => (currentTrack < songs.length - 1 ? currentTrack + 1 : 0));
    };

    const handlePlay = () => {
        setIsPlaying(true);
    };

    const handlePause = () => {
        setIsPlaying(false);
    };

    const handlePlayPauseToggle = (index) => {
        if (currentTrack === index) {
            setIsPlaying(!isPlaying); // Toggle play/pause
            if (!isPlaying) {
                player.current.audio.current.play(); // Resume playback if toggling to play
            } else {
                player.current.audio.current.pause(); // Pause if toggling to pause
            }
        } else {
            setTrackIndex(index);
            setIsPlaying(true); // Play the clicked track
            player.current.audio.current.play(); // Start playing the new track

        }
    };

    const handleLike = (songId) => {
        // Handle liking a song (implement your logic here)
        console.log('Liked song with ID:', songId);
    };

   
    return (  
        <div className='container-fluid'>
            <h5>Create Playlist</h5>
            <div className="row" >
                <div className="input-field col s12" >
                    <input
                        type="text"
                        name="playlistName"
                        value={playlistName}
                        onChange={onTextChange}
                    />
                    <label htmlFor="playlistName">Playlist Name</label>
                </div>
            </div>
            <div className="row">
                <div className="col s12">
                    <button className="btn waves-effect waves-light" onClick={createPlaylist}>
                        Create Playlist
                    </button>
                </div>
            </div>
            <div className='container-fluid' style={{ display: 'flex', flexDirection: 'row' }}>
            <div style={{ flex: '0 0 40%', paddingRight: '10px' }}>
              <table className='table-striped'>
                <thead>
                    <tr>
                        <th>Playlist</th>
                        <th>Play</th>
                        <th>Edit</th>
                        <th>Delete</th>
                    </tr>
                </thead>
                <tbody>
                    {playlistsToShow.map((playlist,index) => (
                        <tr key={playlist.id}>
                            <td>{playlist.playlistName}</td>
                            <td>
                               <button
                                    className={`btn green`}
                                    onClick={() => {
                                        playPlaylist(playlist.id);
                                    }}
                                >
                                    {isPlaying === index ? (
                                        <FontAwesomeIcon icon={faPause} />
                                    ) : (
                                        <FontAwesomeIcon icon={faPlay} />
                                    )}
                                </button>
                            </td>
                            <td>
                            <button className="btn waves-effect waves-light blue"
                                   onClick={()=>{
                                                 editPlaylist(playlist.id,playlist.playlistName);
                                   }}>
                                      Edit
                                </button>
                            </td>    
                            <td>
                            <button className="btn waves-effect waves-light red"
                                onClick={()=>{
                                removePlaylist(playlist.id);
                                }}>
                                  X
                            </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
              </table>
               {/* Pagination for playlists */}
            <ul className='pagination'>
                {Array.from({ length: Math.ceil(playlists.length / playlistsPerPage) }, (_, index) => index + 1).map(
                    (pageNumber) => (
                        <li
                            key={pageNumber}
                            className={`waves-effect ${currentPlaylistPage === pageNumber ? 'active' : ''}`}
                        >
                            <a href='#!' onClick={() => setCurrentPlaylistPage(pageNumber)}>
                                {pageNumber}
                            </a>
                        </li>
                    )
                )}
            </ul>
            </div>
            <div style={{ flex: '1' }}>
            <table className='table-striped'>
                <thead>
                    <tr>
                        <th>Song Name</th>
                        <th>Artist</th>
                        <th>Duration</th>
                        <th>Play/Pause</th>
                        {/* <th>Like</th> */}
                    </tr>
                </thead>
                <tbody>
                    {songsToShow.map((song, index) => (
                        <tr key={song.id}>
                            <td>{song.songName}</td>
                            <td>{song.artistName}</td>
                            <td>{song.duration}</td>
                            <td >
                                <button
                                    className={`btn ${isPlaying && currentTrack === index ? 'red' : 'green'}`}
                                    onClick={() => {
                                        setTrackIndex(index);
                                        handlePlayPauseToggle(index);
                                    }}
                                >
                                    {isPlaying && currentTrack === index ? (
                                        <FontAwesomeIcon icon={faPause} />
                                    ) : (
                                        <FontAwesomeIcon icon={faPlay} />
                                    )}
                                </button>
                            </td>
                            {/* <td>
                                <button className='btn btn-secondary' onClick={() => handleLike(song.id)}>
                                    <FontAwesomeIcon icon={faThumbsUp} /> Like
                                </button>
                            </td> */}
                        </tr>
                    ))}
                </tbody>
            </table>
             {/* Pagination for songs */}
             <ul className='pagination'>
                {Array.from({ length: Math.ceil(playlist.length / songsPerPage) }, (_, index) => index + 1).map(
                    (pageNumber) => (
                        <li
                            key={pageNumber}
                            className={`waves-effect ${currentSongPage === pageNumber ? 'active' : ''}`}
                        >
                            <a href='#!' onClick={() => setCurrentSongPage(pageNumber)}>
                                {pageNumber}
                            </a>
                        </li>
                    )
                )}
            </ul>
            <AudioPlayer
                volume="0.5"
                preload="off" //enable preloading
                src={currentTrack >= 0 ? `http://localhost:8080/api/songs/${playlist[currentTrack]?.id}/aws` : ''}
                showSkipControls
                onClickPrevious={handleClickPrevious}
                onClickNext={handleClickNext}
                onEnded={handleEnd}
                onPlay={handlePlay}
                onPause={handlePause}
                showFilledVolume={true}
                ref={player}
            />
            </div>
         </div>
      </div>
    );
    
}

export default Playlist;
