import React, { useState } from 'react';
import './AdminDashBoard.css';

export default function User() {
  const [email, setEmail] = useState('');
  const [userDetails, setUserDetails] = useState(null);
  const [errorMessage, setErrorMessage] = useState('');

  const handleFetchDetails = async () => {
    const token = sessionStorage.getItem('token');
    if (!token) {
      alert('You must be logged in to access user details.');
      return;
    }

    try {
      const response = await fetch(`http://localhost:8765/customer/profile/find/${email}`, {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.ok) {
        const data = await response.json();
        setUserDetails(data);
        setErrorMessage(''); // Clear any previous error messages
      } else if (response.status === 404) {
        setErrorMessage('No user exists in the database with the entered email.');
        setUserDetails(null); // Clear previous user details
      } else {
        setErrorMessage('Failed to fetch user details. Please try again.');
        setUserDetails(null);
      }
    } catch (error) {
      console.error('Error fetching user details:', error);
      setErrorMessage('An error occurred while fetching user details.');
      setUserDetails(null);
    }
  };

  return (
    <div className="user-details-container">
      <h2 className="user-details-title">Fetch User Details</h2>
      <form className="user-details-form">
        <label>Enter Email</label>
        <input
          type="email"
          className="email-input"
          placeholder="Enter Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <button
          type="button"
          className="fetch-button"
          onClick={handleFetchDetails}
        >
          Fetch
        </button>
      </form>

      {errorMessage && <p className="error-message">{errorMessage}</p>}

      {userDetails && (
        <div className="user-details-info">
          <h3>User Information</h3>
          <p><strong>First Name:</strong> {userDetails.firstName}</p>
          <p><strong>Last Name:</strong> {userDetails.lastName}</p>
          <p><strong>Email:</strong> {userDetails.gmail}</p>
          <p><strong>Date of Birth:</strong> {new Date(userDetails.dateOfBirth).toLocaleDateString()}</p>
          <p><strong>Phone Number:</strong> {userDetails.phoneNumber}</p>
          <h4>Address</h4>
          <p><strong>Street:</strong> {userDetails.addrId.street}</p>
          <p><strong>Apt:</strong> {userDetails.addrId.apt}</p>
          <p><strong>County:</strong> {userDetails.addrId.county}</p>
          <p><strong>State:</strong> {userDetails.addrId.state}</p>
          <p><strong>Country:</strong> {userDetails.addrId.country}</p>
          <p><strong>Postal Code:</strong> {userDetails.addrId.postalCode}</p>
        </div>
      )}
    </div>
  );
}
