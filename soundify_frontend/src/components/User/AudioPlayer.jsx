//import '../../../node_modules/bootstrap/dist/css/bootstrap.min.css'
import ReactH5AudioPlayer from 'react-h5-audio-player';
import React, { useEffect, useState } from 'react';
import 'react-h5-audio-player/lib/styles.css';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'materialize-css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlay, faPause, faThumbsUp } from '@fortawesome/free-solid-svg-icons';


const AudioPlayer = ReactH5AudioPlayer;

const PlayerApp = () => {
    const [songs, setSongs] = useState([]);
    const [currentTrack, setTrackIndex] = useState(0);
    const [isPlaying, setIsPlaying] = useState(false);

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

    const handlePlayPauseToggle = () => {
        setIsPlaying(!isPlaying);
    };

    const handleLike = (songId) => {
        // Handle liking a song (implement your logic here)
        console.log('Liked song with ID:', songId);
    };

    return (
        <div className='container-fluid'>
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
                    {songs.map((song, index) => (
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
                            <td>
                                <button className='btn btn-secondary' onClick={() => handleLike(song.id)}>
                                    <FontAwesomeIcon icon={faThumbsUp} /> Like
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </table>
            <AudioPlayer
                volume="0.5"
                preload="auto" //enable preloading
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