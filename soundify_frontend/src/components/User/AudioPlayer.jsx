//import '../../../node_modules/bootstrap/dist/css/bootstrap.min.css'
import ReactH5AudioPlayer from 'react-h5-audio-player';
import React, { useEffect, useState } from 'react';
import 'react-h5-audio-player/lib/styles.css';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'materialize-css'
import 'material-icons/iconfont/material-icons.css'; // Import the Material Icons CSS

// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
// import { faPlay, faPause, faThumbsUp } from '@fortawesome/free-solid-svg-icons';



const AudioPlayer = ReactH5AudioPlayer;

const PlayerApp = () => {
    const [songs, setSongs] = useState([]);
    const [currentTrack, setTrackIndex] = useState(-1); // Initialize with 0 to play at startup
    const [isPlaying, setIsPlaying] = useState(false);
    const [likedSongs, setLikedSongs] = useState([]);
    const [displayFavorites, setDisplayFavorites] = useState(false);


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
        // Fetch liked songs of the user
        fetchLikedSongs();
    }, []);

    const fetchLikedSongs = () => {
        const userId = window.sessionStorage.getItem("id");

        axios.get(`http://localhost:8080/api/users/${userId}/liked-songs`)
            .then(response => {
                const likedSongIds = response.data.map(song => song.id);
                setLikedSongs(likedSongIds);
            })
            .catch(error => {
                console.error('Error fetching liked songs:', error);
            });
    };

    const handleLike = (songId) => {
        const userId = window.sessionStorage.getItem("id");

        axios.post(`http://localhost:8080/api/users/${userId}/liked-songs/${songId}`)
            .then(response => {
                // Update the likedSongs state
                setLikedSongs([...likedSongs, songId]);
            })
            .catch(error => {
                console.error('Error liking song:', error);
            });
    };

    const handleRemoveLike = songId => {
        const userId = window.sessionStorage.getItem("id");

        axios.delete(`http://localhost:8080/api/users/${userId}/unliked-songs/${songId}`)
            .then(response => {
                // Update the likedSongs state
                setLikedSongs(likedSongs.filter(id => id !== songId));
            })
            .catch(error => {
                console.error('Error removing like:', error);
            });
    };



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
        } else {
            setTrackIndex(index);
            setIsPlaying(true); // Play the clicked track
        }
    };

    // const handleTablePause = () => {
    //     setIsPlaying(false); // Pause when the pause button in the table is clicked
    // };
    const handleToggleFavorites = () => {
        setDisplayFavorites(!displayFavorites);
    };



    return (
        <div className={'container-fluid'}>
            <div className="col s6 right-align">
                <button
                    className={`btn waves-effect waves-light ${displayFavorites ? '' : 'red'}`}
                    onClick={handleToggleFavorites}
                >
                    {displayFavorites ? 'All Songs' : 'Favorites'}
                </button>
            </div>

            <table className='table-striped'>
                <thead>
                    <tr>
                        <th>Song Name</th>
                        <th>Artist</th>
                        <th>Duration</th>
                        <th>Play/Pause</th>
                        <th>Like</th>
                    </tr>
                </thead>
                <tbody>
                    {songs.map((song, index) => {
                        const isLiked = likedSongs.includes(song.id);
                        if (displayFavorites && !isLiked) {
                            return null; // Skip rendering if not a favorite
                        }
                        return (<tr key={song.id}>
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
                                    <i className='material-icons'>
                                        {isPlaying && currentTrack === index ? 'pause' : 'play_arrow'}
                                    </i>
                                    {/* {isPlaying && currentTrack === index ? (
                                        <FontAwesomeIcon icon={faPause} />
                                    ) : (
                                        <FontAwesomeIcon icon={faPlay} />
                                    )} */}
                                </button>
                            </td>
                            <td>
                                <button className={`btn-floating waves-effect waves-light ${likedSongs.includes(song.id) ? "red" : ""}`}
                                    onClick={() => {
                                        if (likedSongs.includes(song.id)) {
                                            handleRemoveLike(song.id);
                                        } else {
                                            handleLike(song.id);
                                        }
                                    }}
                                >
                                    {likedSongs.includes(song.id) ? (
                                        <i className="material-icons red-heart">favorite</i>
                                    ) : (
                                        <i className="material-icons">favorite_border</i>
                                    )}
                                    Like
                                </button>
                            </td>
                        </tr>);
                    })}
                </tbody>
            </table>

            <AudioPlayer
                volume="0.5"
                autoPlay={false} // Set autoPlay to false
                //preload="auto" //enable preloading
                src={currentTrack >= 0 ? `http://localhost:8080/api/songs/${songs[currentTrack]?.id}/aws` : ''}
                showSkipControls
                onClickPrevious={handleClickPrevious}
                onClickNext={handleClickNext}
                onEnded={handleEnd}
                onPlay={handlePlay}
                onPause={handlePause}
            />
        </div>
    );
};
export default PlayerApp;