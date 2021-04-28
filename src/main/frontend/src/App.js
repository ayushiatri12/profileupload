import React, {useState, useEffect,useCallback} from "react";
import logo from "./logo.svg";
import "./App.css";
import {useDropzone} from 'react-dropzone';
import axios from "axios";

const UserProfiles = () => {

  const [userProfiles, setUserProfiles] = useState([]);

  const fetchUserProfiles = () => {
    axios.get("http://localhost:8080/api/v1/user-profile").then(res => {
      console.log(res);
      setUserProfiles(res.data);
    });
  };

  useEffect(() => {
    fetchUserProfiles();
  }, []);

  return userProfiles.map((userProfile, index) => {

    return ( 
      <div key = {index}>
        {userProfile.userProfileID ? (
          <img 
          src={`http://localhost:8080/api/v1/user-profile/${userProfile.userProfileID}/image/download`}
          />
        ):null
        }
        <br/>
        <h1> {userProfile.username}</h1>
        <p> {userProfile.userProfileID}</p>
        <MyDropzone {...userProfile} />
        <br/>
      </div>
    );
  });
};
function App() {
  return (
    <div className="App">
      <UserProfiles/>
    </div>
  );
}

function MyDropzone( {userProfileID} ) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    console.log(file);
    const formData = new FormData();
    formData.append("file", file);
    console.log(userProfileID);
    const baseUrl = "http://localhost:8080/api/v1/user-profile/";
    const Url = baseUrl + userProfileID + "/image/upload";
    // `http://localhost:8080/api/v1/user-profile/${userProfileID}/image/upload`
    console.log(Url)
    axios.post(Url, formData, 
    {
      headers: {
        "Content-Type" : "multipart/form-data"
      }

    }).then (() => { 
      console.log("Image uploaded successfully")
    }).catch(err => {
    console.log(err);
  });
  }, [])
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the image here ...</p> :
          <p>Drag 'n' drop profile image here, or click to select profile image</p>
      }
    </div>
  )
}
export default App;
