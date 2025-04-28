import React, { useContext, useState, useEffect, useCallback } from 'react';
import { UserContext } from './UserContext';
import { useNavigate } from 'react-router-dom';
import './Profile.css';

const Profile = () => {
  const { userDetails } = useContext(UserContext); // Get email from UserContext
  const navigate = useNavigate();
  const [profileData, setProfileData] = useState(null);
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    phoneNumber: '',
    dateOfBirth: '',
    street: '',
    apt: '',
    county: '',
    state: '',
    country: '',
    postalCode: '',
  });
  const [loading, setLoading] = useState(true);
  const [isEditing, setIsEditing] = useState(false);

  // Fetch Profile Data
  const fetchProfile = useCallback(async () => {
    const token = sessionStorage.getItem('token');
    if (!token) {
      alert('You must be logged in to access this data.');
      return
    }

    try {
      const response = await fetch(`http://localhost:8765/customer/profile/find/${userDetails.email}`, {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.ok) {
        const data = await response.json();
        // Populate profile data with default values for missing fields
        setProfileData({
          firstName: data.firstName || '',
          lastName: data.lastName || '',
          phoneNumber: data.phoneNumber || '',
          dateOfBirth: data.dateOfBirth || '',
          addrId: {
            street: data.addrId?.street || '',
            apt: data.addrId?.apt || '',
            county: data.addrId?.county || '',
            state: data.addrId?.state || '',
            country: data.addrId?.country || '',
            postalCode: data.addrId?.postalCode || '',
          },
        });
      } else if (response.status === 404) {
        setProfileData(null);
        alert('No user profile found. Please update your details.');
      } else {
        alert('Failed to fetch user profile.');
      }
    } catch (error) {
      console.error('Error fetching profile:', error);
    } finally {
      setLoading(false);
    }
  }, [userDetails.email]);

  useEffect(() => {
    fetchProfile();
  }, [fetchProfile]);

  // Handle input changes
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  // Save Profile Data
  const handleSave = async () => {
    const token = sessionStorage.getItem('token');
    if (!token) {
      alert('You must be logged in to update this data.');
      return;
    }

    try {
      const response = await fetch(`http://localhost:8765/customer/profile/update/${userDetails.email}`, {
        method: 'POST',
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          customer: {
            firstName: formData.firstName || null,
            lastName: formData.lastName || null,
            phoneNumber: formData.phoneNumber || null,
            dateOfBirth: formData.dateOfBirth || null,
          },
          address: {
            street: formData.street || null,
            apt: formData.apt || null,
            county: formData.county || null,
            state: formData.state || null,
            country: formData.country || null,
            postalCode: formData.postalCode || null,
          },
        }),
      });

      if (response.ok) {
        const updatedData = await response.json();
        setProfileData(updatedData);
        alert('Profile updated successfully!');
        setIsEditing(false);

        // Refetch the profile to ensure the latest data is displayed
        fetchProfile();
      } else {
        alert('Failed to update profile.');
      }
    } catch (error) {
      console.error('Error updating profile:', error);
    }
  };

  // Handle Dropdown Changes
  const handleDropdownChange = (e) => {
    const value = e.target.value;
    if (value === 'update') {
      setIsEditing(true);
      setFormData({
        firstName: profileData?.firstName || '',
        lastName: profileData?.lastName || '',
        phoneNumber: profileData?.phoneNumber || '',
        dateOfBirth: profileData?.dateOfBirth?.split('T')[0] || '',
        street: profileData?.addrId?.street || '',
        apt: profileData?.addrId?.apt || '',
        county: profileData?.addrId?.county || '',
        state: profileData?.addrId?.state || '',
        country: profileData?.addrId?.country || '',
        postalCode: profileData?.addrId?.postalCode || '',
      });
    } else if (value === 'view') {
      setIsEditing(false);
    } else if (value === 'logout') {
      navigate('/logout');
    }
  };

  // Loading State
  if (loading) {
    return <p>Loading profile...</p>;
  }

  return (
    <div className="profile-container">
      <h2>User Profile</h2>
      <div className="profile-dropdown">
        <select onChange={handleDropdownChange} value={isEditing ? 'update' : 'view'}>
          <option value="view">View Profile</option>
          <option value="update">Update Profile</option>
          <option value="logout">Logout</option>
        </select>
      </div>

      {!isEditing ? (
        profileData ? (
          <div className="profile-details">
            <p><strong>First Name:</strong> {profileData.firstName || 'N/A'}</p>
            <p><strong>Last Name:</strong> {profileData.lastName || 'N/A'}</p>
            <p><strong>Email:</strong> {userDetails.email}</p>
            <p><strong>Phone Number:</strong> {profileData.phoneNumber || 'N/A'}</p>
            <p><strong>Date of Birth:</strong> {profileData.dateOfBirth ? profileData.dateOfBirth.split('T')[0] : 'N/A'}</p>
            <p><strong>Address:</strong></p>
            <p>{profileData.addrId.street || 'N/A'}, {profileData.addrId.apt || 'N/A'}</p>
            <p>
              {profileData.addrId.county || 'N/A'}, {profileData.addrId.state || 'N/A'}, {profileData.addrId.country || 'N/A'} - {profileData.addrId.postalCode || 'N/A'}
            </p>

            {/* Go Back Button */}
            <button onClick={() => navigate('/user')} className="go-back-button">
              Go Back to Dashboard
            </button>
          </div>
        ) : (
          <>
          <p>No profile data available. Please update your details.</p>

          <button onClick={() => navigate('/user')} className="go-back-button">
              Go Back to Dashboard
            </button>
            </>
        )
      ) : (
        <form className="update-form">
          <label>
            First Name:
            <input type="text" name="firstName" value={formData.firstName} onChange={handleInputChange} />
          </label>
          <label>
            Last Name:
            <input type="text" name="lastName" value={formData.lastName} onChange={handleInputChange} />
          </label>
          <label>
            Phone Number:
            <input type="text" name="phoneNumber" value={formData.phoneNumber} onChange={handleInputChange} />
          </label>
          <label>
            Date of Birth:
            <input type="date" name="dateOfBirth" value={formData.dateOfBirth} onChange={handleInputChange} />
          </label>
          <label>
            Street:
            <input type="text" name="street" value={formData.street} onChange={handleInputChange} />
          </label>
          <label>
            Apartment:
            <input type="text" name="apt" value={formData.apt} onChange={handleInputChange} />
          </label>
          <label>
            County:
            <input type="text" name="county" value={formData.county} onChange={handleInputChange} />
          </label>
          <label>
            State:
            <input type="text" name="state" value={formData.state} onChange={handleInputChange} />
          </label>
          <label>
            Country:
            <input type="text" name="country" value={formData.country} onChange={handleInputChange} />
          </label>
          <label>
            Postal Code:
            <input type="text" name="postalCode" value={formData.postalCode} onChange={handleInputChange} />
          </label>
          <button type="button" onClick={handleSave}>Save</button>
        </form>
      )}
    </div>
  );
};

export default Profile;
