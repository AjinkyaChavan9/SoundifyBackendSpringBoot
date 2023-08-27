import React, { useEffect, useState } from "react";
import "./player.css";
import { Navigate, useLocation } from "react-router-dom";
//import apiClient from "../../spotify";
import SongCard from "../../musicPlayerComponents/songCard";
import Queue from "../../musicPlayerComponents/queue";
import AudioPLayer from "../../musicPlayerComponents/audioPlayer";
import Widgets from "../../musicPlayerComponents/widgets";
import axios from "axios";

export default function Player() {
  const location = useLocation();
  const [tracks, setTracks] = useState([]);
  const [currentTrack, setCurrentTrack] = useState({});
  const [currentIndex, setCurrentIndex] = useState(0);

  // This useEffect runs only once when the component mounts
  useEffect(() => {
    if (tracks.length > 0) {
      setCurrentTrack(tracks[0]); // Set the first song initially
    }
  }, []);
  
  useEffect(() => {
  if (location.state) {
      
       axios.get(`http://localhost:8080/api/playlists/songs/${location.state.id}`)
        .then((res) => {
          setTracks(res.data); //list of songs
          setCurrentTrack(res.data[0]); //first song
        })
        .catch((error) => {
          console.log(error);
        });
    }
  }, [location.state]);

  useEffect(() => {
    setCurrentTrack(tracks[currentIndex]);
  }, [currentIndex, tracks]);

  return (
    <div className="screen-container flex">
      <div className="left-player-body">
        <AudioPLayer
          currentTrack={currentTrack}
          total={tracks}
          currentIndex={currentIndex}
          setCurrentIndex={setCurrentIndex}
        />
        <Widgets artistID={currentTrack?.album?.artists[0]?.id} />
      </div>
      <div className="right-player-body">
        <SongCard song={currentTrack} />
        <Queue tracks={tracks} setCurrentIndex={setCurrentIndex}/>
      </div>
    </div>
  );
}
