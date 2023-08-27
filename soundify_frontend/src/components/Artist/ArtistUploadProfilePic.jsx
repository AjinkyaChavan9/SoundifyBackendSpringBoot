import React, { useState } from 'react';
import axios from 'axios';
import '../../../node_modules/bootstrap/dist/css/bootstrap.min.css';
import { useNavigate } from 'react-router-dom';

function ArtistUploadProfilePic() {
    const artistId = window.sessionStorage.getItem("id");

    // const [songName, setSongName] = useState('');
    // const [releaseDate, setReleaseDate] = useState('');
    // const [duration, setDuration] = useState('00:00:00');
    const [imageFile, setImageFile] = useState(null);
    const [selectedImage, setSelectedImage] = useState(null);
    const [uploadStatus, setUploadStatus] = useState('');
    var navigate = useNavigate();

    const handleFileChange = (event) => {
        const selectedFile = event.target.files[0];
        console.log(selectedFile)
        setImageFile(selectedFile);
        console.log(imageFile)


        // Display the selected image
        const reader = new FileReader();
        reader.onload = (e) => {
            setSelectedImage(e.target.result);
        };
        reader.readAsDataURL(selectedFile);
    }
    

    const handleUpload = async () => {
        const formData = new FormData();
        
        formData.append('imageFile', imageFile);//parameter name must be same as that in backend
        console.log(imageFile);
        // formData.append('songName', songName);
        // formData.append('releaseDate', releaseDate);
        // formData.append('duration', duration);

        try {
            const response = await axios.post(`http://127.0.0.1:8080/api/artists/${artistId}/image`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            });

            if (response.status === 201) {
                console.log('Profile Pic upload successful', response.data);
                setUploadStatus('success');
                localStorage.setItem('profilePicUploaded', 'true'); // Set the flag
                setTimeout(() => {
                    setUploadStatus('');
                    navigate('/artistdashboard')
                }, 3000); // Display success status for 3 seconds
                
                
            } else {
                console.error('Error uploading Profile Picture', response.data);
                setUploadStatus('error');
                setTimeout(() => {
                    setUploadStatus('');
                }, 3000); // Display error status for 3 seconds
            }
        } catch (error) {
            console.error('Error uploading Profile Picture', error);
            setUploadStatus('error');
            setUploadStatus('error');
            setTimeout(() => {
                setUploadStatus('');
            }, 3000); // Display error status for 3 seconds
        }
    };

    return (
        <div>
            <h2>Upload Profile Pic</h2>
            <form>
                <div className="mb-3">
                    <label className="form-label">Choose a file</label>
                    <input type="file" className="form-control" accept="image/*" onChange={handleFileChange} />
                </div>
                <div>
                {selectedImage && <img src={selectedImage} alt="Selected" style={{ maxWidth: '50%' }} />}
                </div>
                <br/>
                <button type="button" className="btn btn-primary" onClick={handleUpload}>Upload Profile Pic</button>
            </form>
            {uploadStatus === 'success' && <p className='card-panel teal lighten-2'>Upload successful!</p>}
            {uploadStatus === 'error' && <p className='card-panel red lighten-2'>Upload failed. Please try again later.</p>}
        </div>
    );

}

export default ArtistUploadProfilePic;
