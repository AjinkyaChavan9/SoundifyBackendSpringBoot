import React from "react";
import SongImage from "./songImage";
import SongInfo from "./songInfo";
import "./songCard.css";

export default function SongCard({song}) {
  return (
    <div className="songCard-body flex">
      <SongImage songId={song?.id} />
      <SongInfo song={song} />
    </div>
  );
}
