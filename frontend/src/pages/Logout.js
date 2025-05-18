import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export default function Logout() {
  const navigate = useNavigate();

  useEffect(() => {

    sessionStorage.removeItem('token');
    alert('You have been logged out successfully.');

    navigate('/');
  }, [navigate]);

  return (
    <div className="logout-container">
      <p>Logging out...</p>
    </div>
  );
}
