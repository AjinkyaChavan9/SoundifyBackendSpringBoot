import React from "react";
import "./songInfo.css";

export default function SongInfo({ song }) {
  // const artists = [];
  // album?.artists?.forEach((element) => {
  //   artists.push(element.name);
  // });

  return (
    <div className="songInfo-card">
      <div className="songName-container">
        <div className="marquee">
          <p>{song?.songName}</p>
        </div>
      </div>
      <div className="song-info">
        <p>Duration:{song?.duration}</p>
      </div>
      <div className="song-release">
        <p>Release Date: {song?.releaseDate}</p>
      </div>
    </div>
  );
}
