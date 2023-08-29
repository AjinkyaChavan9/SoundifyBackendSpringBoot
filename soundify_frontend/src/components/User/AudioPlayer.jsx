//import '../../../node_modules/bootstrap/dist/css/bootstrap.min.css'
import ReactH5AudioPlayer from 'react-h5-audio-player';
import React, { useEffect, useRef, useState } from 'react';
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
    const [searchQuery, setSearchQuery] = useState('');
    const [currentPage, setCurrentPage] = useState(1);
    const songsPerPage = 4;
    const [currentSongId, setCurrentSongId] = useState(null); // Use this to track the current song ID


    useEffect(() => {
        // Fetch songs from the API
        fetch('/api/songs/songs')
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

        axios.get(`/api/users/${userId}/liked-songs`)
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

        axios.post(`/api/users/${userId}/liked-songs/${songId}`)
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

        axios.delete(`/api/users/${userId}/unliked-songs/${songId}`)
            .then(response => {
                // Update the likedSongs state
                setLikedSongs(likedSongs.filter(id => id !== songId));
            })
            .catch(error => {
                console.error('Error removing like:', error);
            });
    };



    // const handleClickPrevious = () => {
    //     console.log("click previous");
    //     setTrackIndex(currentTrack => (currentTrack > 0 ? currentTrack - 1 : songs.length - 1));
    // };

    // const handleClickNext = () => {
    //     console.log("click next");

    //     setTrackIndex(currentTrack => (currentTrack < songs.length - 1 ? currentTrack + 1 : 0));
    // };

    // const handleEnd = () => {
    //     console.log("end");

    //     setTrackIndex(currentTrack => (currentTrack < songs.length - 1 ? currentTrack + 1 : 0));
    // };

    const handleClickPrevious = () => {
        if (currentSongId !== null) {
            const currentIndex = filteredSongs.findIndex(song => song.id === currentSongId);
            const previousIndex = (currentIndex - 1 + filteredSongs.length) % filteredSongs.length;
            setCurrentSongId(filteredSongs[previousIndex].id);
        }
    };
    
    const handleClickNext = () => {
        if (currentSongId !== null) {
            const currentIndex = filteredSongs.findIndex(song => song.id === currentSongId);
            const nextIndex = (currentIndex + 1) % filteredSongs.length;
            setCurrentSongId(filteredSongs[nextIndex].id);
        }
    };

    const handleEnd = () => {
        if (currentSongId !== null) {
            const currentIndex = filteredSongs.findIndex(song => song.id === currentSongId);
            const nextIndex = (currentIndex + 1) % filteredSongs.length;
            setCurrentSongId(filteredSongs[nextIndex].id);
        }
    };
    

    const handlePlay = () => {
        setIsPlaying(true);
    };

    const handlePause = () => {
        setIsPlaying(false);
    };

    // const handlePlayPauseToggle = (index) => {
    //     if (currentTrack === index) {
    //         setIsPlaying(!isPlaying); // Toggle play/pause
    //         if (!isPlaying) {
    //             player.current.audio.current.play(); // Resume playback if toggling to play
    //         } else {
    //             player.current.audio.current.pause(); // Pause if toggling to pause
    //         }
    //     } else {
    //         setTrackIndex(index);
    //         setIsPlaying(true); // Play the clicked track
    //         player.current.audio.current.play(); // Start playing the new track

    //     }
    // };
    const handlePlayPauseToggle = (songId) => {
        if (currentSongId === songId) {
            setIsPlaying(!isPlaying); // Toggle play/pause state
    
            if (!isPlaying) {
                player.current.audio.current.play(); // Resume playback if toggling to play
            } else {
                player.current.audio.current.pause(); // Pause if toggling to pause
            }
        } else {
            setCurrentSongId(songId); // Update the current song ID
            setIsPlaying(true); // Play the clicked track
            player.current.audio.current.play(); // Start playing the new track
        }
    };
    

    const player = useRef();

    // const handleTablePause = () => {
    //     setIsPlaying(false); // Pause when the pause button in the table is clicked
    // };
    const handleToggleFavorites = () => {
        setDisplayFavorites(!displayFavorites);
    };


    //Search/Pagination Logic
    const handleSearchChange = (event) => {
        setSearchQuery(event.target.value);
        setCurrentPage(1); // Reset page when search query changes
    };

    const handlePageChange = (page) => {
        setCurrentPage(page);
    };

    const filteredSongs = songs.filter(song => {
        const normalizedSearch = searchQuery.toLowerCase();
        return (
            song.songName.toLowerCase().includes(normalizedSearch) ||
            song.artistName.toLowerCase().includes(normalizedSearch)
        );
    });

    const startIndex = (currentPage - 1) * songsPerPage;
    const endIndex = startIndex + songsPerPage;
    const songsToShow = filteredSongs.slice(startIndex, endIndex);
    const totalPages = Math.ceil(filteredSongs.length / songsPerPage);
    const pageNumbers = Array.from({ length: totalPages }, (_, index) => index + 1);




    return (
        <div className={'container'}>
            <div className="col s6 right-align">
                <button
                    className={`btn waves-effect waves-light ${displayFavorites ? '' : 'red'}`}
                    onClick={handleToggleFavorites}
                >
                    {displayFavorites ? 'All Songs' : 'Favorites'}
                </button>
            </div>

            {/* Search input */}
            <div className="input-field col s6">
                <input
                    id="search"
                    type="text"
                    value={searchQuery}
                    onChange={handleSearchChange}
                />
                <label htmlFor="search">Search</label>
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
                    {songsToShow.map((song, index) => {
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
                                        className={`btn ${isPlaying && currentSongId === song.id ? 'red' : 'green'}`}
                                        onClick={() => handlePlayPauseToggle(song.id)}
                                    >
                                        <i className='material-icons'>
                                            {isPlaying && currentSongId === song.id ? 'pause' : 'play_arrow'}
                                        </i>
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

             {/* Pagination */}
             <ul className="pagination">
                {pageNumbers.map((page) => (
                    <li key={page} className={`waves-effect ${currentPage === page ? 'active' : ''}`}>
                        <a href="#!" onClick={() => handlePageChange(page)}>
                            {page}
                        </a>
                    </li>
                ))}
            </ul>

            <AudioPlayer
                volume="0.5"
                preload="off" //enable preloading
                autoPlay={false} // Set autoPlay to false
                //preload="auto" //enable preloading
                src={currentSongId  !== null ? `/api/songs/${currentSongId}/aws` : ''}
                showSkipControls
                showFilledVolume={true}
                onClickPrevious={handleClickPrevious}
                onClickNext={handleClickNext}
                onEnded={handleEnd}
                onPlay={handlePlay}
                onPause={handlePause}
                ref={player}
            />
        </div>
    );
};
export default PlayerApp;