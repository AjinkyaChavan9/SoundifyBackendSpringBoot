import React, { useEffect, useState } from "react";
import axios from "axios";
import "./songImage.css";

export default function SongImage({songId}) {
  const [imgUrl, setImgUrl] = useState(null);
  const id = songId;
  useEffect(() => {
    async function fetchImage() {
      try {
        const response = await axios.get(`http://localhost:8080/api/songs/${id}/image`, {
          responseType: 'arraybuffer', // Set the response type to 'arraybuffer' for binary data
        });

        // Create a Blob from the response data
        const blob = new Blob([response.data], { type: 'image/jpeg' });

        // Convert the Blob to a data URL
        const imageUrl = URL.createObjectURL(blob);

        setImgUrl(imageUrl);
      } catch (error) {
        console.error('Error fetching image:', error);
      }
    }

    fetchImage();
  }, [songId]);

  return (
    <div className="songImage flex">
      {imgUrl && (
        <>
          <img src={imgUrl} alt="song art" className="songImage-art" />
          <div className="songImage-shadow">
            <img src={imgUrl} alt="shadow" className="songImage-shadow" />
          </div>
        </>
      )}
    </div>
  );
}
