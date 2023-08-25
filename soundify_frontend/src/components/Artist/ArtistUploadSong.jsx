import React, { useState } from 'react';
import axios from 'axios';
import '../../../node_modules/bootstrap/dist/css/bootstrap.min.css';

function ArtistUploadSong() {
    const artistId = window.sessionStorage.getItem("id");

    const [songName, setSongName] = useState('');
    const [releaseDate, setReleaseDate] = useState('');
    const [duration, setDuration] = useState('00:00:00');
    const [file, setFile] = useState(null);
    const [uploadStatus, setUploadStatus] = useState('');

    const handleFileChange = (event) => {
        setFile(event.target.files[0]);
    };

    const handleUpload = async () => {
        const formData = new FormData();
        formData.append('file', file);
        formData.append('songName', songName);
        formData.append('releaseDate', releaseDate);
        formData.append('duration', duration);

        try {
            const response = await axios.post(`http://127.0.0.1:8080/api/artists/aws/${artistId}/song`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });

            if (response.status === 201) {
                setUploadStatus('success');
                console.log('Song upload successful', response.data);
            } else {
                setUploadStatus('error');
                console.error('Error uploading song', response.data);
            }
        } catch (error) {
            setUploadStatus('error');
            console.error('Error uploading song', error);
        }
    };

    return (
        <div>
            <h2>Upload a Song</h2>
            <form>
                <div className="mb-3">
                    <label className="form-label">Choose a file</label>
                    <input type="file" className="form-control" accept="audio/*" onChange={handleFileChange} />
                </div>
                <div className="mb-3">
                    <label className="form-label">Song Name</label>
                    <input type="text" className="form-control" value={songName} onChange={(e) => setSongName(e.target.value)} />
                </div>
                <div className="mb-3">
                    <label className="form-label">Release Date</label>
                    <input type="date" className="form-control" value={releaseDate} onChange={(e) => setReleaseDate(e.target.value)} />
                </div>
                <div className="mb-3">
                    <label className="form-label">Duration (hh:mm:ss)</label>
                    <input type="text" className="form-control" value={duration} onChange={(e) => setDuration(e.target.value)} />
                </div>
                <button type="button" className="btn btn-primary" onClick={handleUpload}>Upload Song</button>
            </form>
            {uploadStatus === 'success' && <p className='card-panel teal lighten-2'>Upload successful! Clear upload Page and navigate to artist dashboard</p>}
            {uploadStatus === 'error' && <p className='card-panel red lighten-2'>Upload failed. Please try again later.</p>}
        </div>
    );
}

export default ArtistUploadSong;
