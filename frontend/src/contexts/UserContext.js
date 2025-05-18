import React, { createContext, useState, useEffect } from 'react';

export const UserContext = createContext();

export const UserProvider = ({ children }) => {
  const [userDetails, setUserDetails] = useState(() => {
    // Retrieve from sessionStorage on initialization
    const storedDetails = sessionStorage.getItem('userDetails');
    return storedDetails ? JSON.parse(storedDetails) : { email: '', username: '' };
  });

  useEffect(() => {
    // Sync userDetails with sessionStorage whenever it changes
    if (userDetails && userDetails.username) {
      sessionStorage.setItem('userDetails', JSON.stringify(userDetails));
    } else {
      sessionStorage.removeItem('userDetails'); // Clear if userDetails is empty
    }
  }, [userDetails]);

  return (
    <UserContext.Provider value={{ userDetails, setUserDetails }}>
      {children}
    </UserContext.Provider>
  );
};
