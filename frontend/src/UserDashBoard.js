import React, { useContext } from 'react';
import { UserContext } from './UserContext';
import Header from './header';
import './UserDashBoard.css';
import { MdPhoneAndroid, MdWifi, MdLiveTv } from 'react-icons/md';
import { useNavigate } from 'react-router-dom';

const UserDashboard = () => {
  const { userDetails } = useContext(UserContext);
  const navigate = useNavigate();

  return (
    <div className="dashboard-container">
     
      <h1 className="dashboard-heading">Welcome, {userDetails.username}!</h1>

      
      <div className="top-right">
        <Header />
      </div>

     
      <div className="dashboard-cards">
        <div className="dashboard-card mobile">
          <MdPhoneAndroid className="card-icon" />
          <h2>Mobile Plans</h2>
          <p>Discover exclusive mobile plans tailored just for you.</p>
          <button onClick={() => navigate('/user/mobile')}>Explore</button>
        </div>

        <div className="dashboard-card wifi">
          <MdWifi className="card-icon" />
          <h2>WiFi Plans</h2>
          <p>Experience high-speed internet for your home or business.</p>
          <button onClick={() => navigate('/user/wifi')}>Explore</button>
        </div>

        <div className="dashboard-card tv">
          <MdLiveTv className="card-icon" />
          <h2>TV Plans</h2>
          <p>Access premium TV channels and on-demand entertainment.</p>
          <button onClick={() => navigate('/user/tv')}>Explore</button>
        </div>
      </div>
    </div>
  );
};

export default UserDashboard;
